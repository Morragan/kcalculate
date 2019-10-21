package com.example.dietapp.ui.createmeal

interface CreateMealView {
    fun showConnectionError()
    fun logout()
    fun onSuccess()
    fun showError()
}