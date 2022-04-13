package br.com.exampleviewstate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.exampleviewstate.LoginViewModel.LoginViewState.*
import br.com.exampleviewstate.databinding.ActivityMainBinding
import br.com.exampleviewstate.delegate.*
import org.koin.androidx.viewmodel.ext.android.viewModel

open class LoginActivity :
    AppCompatActivity(),
    StateDelegate by StateDelegation(),
    LoadingDelegate by LoadingDelegation() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        registerLoadingDelegate(this, binding.root)
        registerDelegation(lifecycle, viewModel, this)
        setupViews()
    }

    private fun setupViews() {
        binding.btnLogin.setOnClickListener {
            viewModel.onButtonClick()
        }
    }

    override fun renderState(viewState: ViewState) {
        when (viewState) {
            is ShowInvalidDataState -> {

            }
            is LoginSuccessState -> {
                //Handle login success
                viewState.loginData
            }
            is ShowDialogState -> {
                //Handle show dialog
            }
        }
    }

}