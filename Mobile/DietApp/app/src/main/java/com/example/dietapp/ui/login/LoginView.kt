package com.example.dietapp.ui.login

interface LoginView {
    fun showConnectionFailure()
    fun showErrorMessage(message: String)
    fun startHomeActivity()
}