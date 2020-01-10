package com.example.dietapp.ui.recordmeal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.api.exceptions.NotAuthorizedException
import com.example.dietapp.db.repositories.AccountRepository
import com.example.dietapp.db.repositories.MealEntriesRepository
import com.example.dietapp.db.repositories.MealsRepository
import com.example.dietapp.models.dto.RecordMealDTO
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecordMealViewModel @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val mealEntriesRepository: MealEntriesRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    val userMeals = mealsRepository.allMeals
    val found3rdPartyMeals = mealsRepository.found3rdPartyMeals
    val loggedIn = accountRepository.loggedIn

    fun fetchMeals() = viewModelScope.launch {
        try {
            mealsRepository.fetchMeals()
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        }
    }

    fun recordMeal(meal: RecordMealDTO) = viewModelScope.launch {
        try{
            mealEntriesRepository.recordMeal(meal)
        }
        catch (e: NotAuthorizedException){
            accountRepository.logout()
        }
    }

    fun find3rdPartyMeals(query: String) = viewModelScope.launch {
        try{
            mealsRepository.find3rdPartyMeals(query)
        } catch (e: NotAuthorizedException){
            accountRepository.logout()
        }
    }
}