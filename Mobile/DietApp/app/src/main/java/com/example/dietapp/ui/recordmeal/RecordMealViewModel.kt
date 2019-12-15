package com.example.dietapp.ui.recordmeal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.db.repositories.MealEntriesRepository
import com.example.dietapp.db.repositories.MealsRepository
import com.example.dietapp.models.dto.RecordMealDTO
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecordMealViewModel @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val mealEntriesRepository: MealEntriesRepository
) : ViewModel() {

    val userMeals = mealsRepository.allMeals
    val found3rdPartyMeals = mealsRepository.found3rdPartyMeals

    fun fetchMeals() = viewModelScope.launch {
        mealsRepository.fetchMeals()
    }

    fun recordMeal(meal: RecordMealDTO) = viewModelScope.launch {
        mealEntriesRepository.recordMeal(meal)
    }

    fun find3rdPartyMeals(query: String) = viewModelScope.launch {
        mealsRepository.find3rdPartyMeals(query)
    }
}