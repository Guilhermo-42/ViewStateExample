package br.com.exampleviewstate.delegate

import android.util.Log
import androidx.lifecycle.*
import br.com.exampleviewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

interface MediationDelegate: DefaultLifecycleObserver {
    fun <T : ViewState> intoMediator(): MutableLiveData<T>
    fun mediator(): LiveData<ViewState>
    fun ViewModel.load(asyncBlock: suspend () -> Result<Any>): Job
    fun ViewModel.launch(asyncBlock: suspend () -> Result<Any>): Job
}

class MediationDelegation : MediationDelegate {

    private val viewStateLiveData by lazy {
        MediatorLiveData<ViewState>()
    }

    override fun <T : ViewState> intoMediator(): MutableLiveData<T> {
        val liveData = MutableLiveData<T>()
        viewStateLiveData.addSource(liveData) {
            //Timber?
            Log.i("ViewState", "-> ${it.javaClass.simpleName}")
            viewStateLiveData.value = it
        }
        return liveData
    }

    override fun mediator(): LiveData<ViewState> {
        return viewStateLiveData
    }

    override fun ViewModel.load(asyncBlock: suspend () -> Result<Any>): Job {
        Log.i("ViewState", "-> ${ViewState.Loading.javaClass.simpleName}")
        viewStateLiveData.postValue(ViewState.Loading)
        return viewModelScope.launch {
            asyncBlock.invoke()
                .onSuccess {
                    Log.i("ViewState", "-> ${ViewState.Success.javaClass.simpleName}")
                    viewStateLiveData.postValue(ViewState.Success)
                }
                .onFailure {
                    Log.i("ViewState", "-> ${ViewState.Failure.javaClass.simpleName}")
                    viewStateLiveData.postValue(ViewState.Failure)
                }
        }}

    override fun ViewModel.launch(asyncBlock: suspend () -> Result<Any>): Job {
        return viewModelScope.launch {
            asyncBlock.invoke()
        }}

}