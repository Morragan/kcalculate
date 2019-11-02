package com.example.dietapp.ui.register

interface RegisterView {
    fun showCalorieLimit(calorieLimit: Int, carbsLimit: Int, fatLimit: Int, proteinLimit: Int)
    fun showConnectionFailure()
    fun showErrorMessage(message: String)
    fun startLoginActivity()
}