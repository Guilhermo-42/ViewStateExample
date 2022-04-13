package br.com.exampleviewstate

abstract class ViewState {
    object Success: ViewState()
    object Failure: ViewState()
    object Loading: ViewState()
}