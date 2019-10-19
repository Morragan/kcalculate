package com.example.dietapp.ui.recordmeal

import com.example.dietapp.models.MealDTO

interface RecordMealView {
    fun showConnectionError()
    fun replaceUserMeals(meals: List<MealDTO>)
    fun logout()
    fun stopCpbAnimation(isSuccess: Boolean)
}