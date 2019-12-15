package com.example.dietapp.ui.createmeal

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.db.repositories.MealsRepository
import com.example.dietapp.models.dto.CreateMealDTO
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateMealViewModel @Inject constructor(private val mealsRepository: MealsRepository) :
    ViewModel() {

    private val meals = mealsRepository.allMeals
    val isLoading =
        Transformations.map(meals) { meals.value?.find { meal -> meal.isLoading } != null }

    fun createMeal(meal: CreateMealDTO) = viewModelScope.launch {
        mealsRepository.addMeal(meal)
    }
}