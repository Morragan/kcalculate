package com.example.dietapp.ui.createmeal

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.api.exceptions.NotAuthorizedException
import com.example.dietapp.db.repositories.AccountRepository
import com.example.dietapp.db.repositories.MealsRepository
import com.example.dietapp.models.dto.CreateMealDTO
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateMealViewModel @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val accountRepository: AccountRepository
) :
    ViewModel() {

    private val meals = mealsRepository.allMeals
    val isLoading =
        Transformations.map(meals) { meals.value?.find { meal -> meal.isLoading } != null }
    val loggedIn = accountRepository.loggedIn

    fun createMeal(meal: CreateMealDTO) = viewModelScope.launch {
        try {
            mealsRepository.addMeal(meal)
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        }
    }
}