package br.com.exampleviewstate

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import br.com.exampleviewstate.data.IDataSource
import br.com.exampleviewstate.delegate.MediationDelegate
import br.com.exampleviewstate.delegate.MediationDelegation
import kotlinx.coroutines.CompletionHandler

class LoginViewModel(private val dataSource: IDataSource) :
    ViewModel(),
    MediationDelegate by MediationDelegation() {

    private val stateLiveData = intoMediator<LoginViewState>()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        fetchLoginData()
    }

    fun fetchLoginData() {
        load {
            dataSource.fetchData()
                .onSuccess {
                    stateLiveData.postValue(LoginViewState.LoginSuccessState(it))
                }.onFailure {
                    stateLiveData.postValue(LoginViewState.ShowInvalidDataState)
                }
        }
    }

    fun onButtonClick() {
        launch {
            dataSource.fetchOtherData()
                .onSuccess {
                    stateLiveData.postValue(LoginViewState.ShowToastState(it))
                }
                .onFailure {
                    stateLiveData.postValue(LoginViewState.ErrorFetchingOtherData)
                }
        }
    }

    sealed class LoginViewState : ViewState() {
        data class LoginSuccessState(val loginData: String) : LoginViewState()
        object ShowInvalidDataState : LoginViewState()
        object ShowDialogState : LoginViewState()
        data class ShowToastState(val value: Int) : LoginViewState()
        object ErrorFetchingOtherData : LoginViewState()
    }

}