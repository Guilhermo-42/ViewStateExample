package br.com.exampleviewstate.delegate

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import br.com.exampleviewstate.ViewState

interface StateDelegate {
    fun StateDelegate.registerDelegation(
        lifecycle: Lifecycle,
        viewModel: MediationDelegate,
        loadingDelegate: LoadingDelegate? = null
    )
    fun renderState(viewState: ViewState)
}

class StateDelegation : DefaultLifecycleObserver, StateDelegate {

    private lateinit var viewModel: MediationDelegate

    private lateinit var delegate: StateDelegate

    private var loadingDelegate: LoadingDelegate? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModel.mediator().observe(owner) {
            renderState(it)
            delegate.renderState(it)
        }
    }

    override fun StateDelegate.registerDelegation(
        lifecycle: Lifecycle,
        viewModel: MediationDelegate,
        loadingDelegate: LoadingDelegate?
    ) {
        this@StateDelegation.viewModel = viewModel
        this@StateDelegation.delegate = this
        this@StateDelegation.loadingDelegate = loadingDelegate
        lifecycle.addObserver(this@StateDelegation)
        lifecycle.addObserver(viewModel)
    }

    override fun renderState(viewState: ViewState) {
        when (viewState) {
            is ViewState.Success -> {
                loadingDelegate?.onSuccess()
            }
            is ViewState.Failure -> {
                loadingDelegate?.onFailure()
            }
            is ViewState.Loading -> {
                loadingDelegate?.onLoading()
            }
        }
    }

}