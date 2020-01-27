package com.example.dietapp.ui.recordmeal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.api.exceptions.BadRequestException
import com.example.dietapp.api.exceptions.NotAuthorizedException
import com.example.dietapp.db.repositories.AccountRepository
import com.example.dietapp.db.repositories.MealEntriesRepository
import com.example.dietapp.db.repositories.MealsRepository
import com.example.dietapp.models.dto.CreateMealDTO
import com.example.dietapp.models.dto.RecordMealDTO
import com.example.dietapp.utils.ViewState
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecordMealViewModel @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val mealEntriesRepository: MealEntriesRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    val userMeals = mealsRepository.allMeals
    val found3rdPartyMeals = mealsRepository.found3rdPartyMeals
    val foundBarcodeMeal = mealsRepository.foundBarcodeMeal
    val loggedIn = accountRepository.loggedIn
    val userMealsFragmentViewState = MutableLiveData<ViewState>(
        ViewState.LOADING)
    val allMealsFragmentViewState = MutableLiveData<ViewState>(
        ViewState.DEFAULT)
    val isLoadingDialogVisible = MutableLiveData<Boolean>()
    val isBarcodeFoundDialogVisible = MutableLiveData<Boolean>()
    val isBarcodeNotFoundDialogVisible = MutableLiveData<Boolean>()

    fun fetchMeals() = viewModelScope.launch {
        try {
            mealsRepository.fetchMeals()
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun recordMeal(meal: RecordMealDTO) = viewModelScope.launch {
        try {
            mealEntriesRepository.recordMeal(meal)
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
            isLoadingDialogVisible.postValue(false)
        } catch (e: Exception) {
            e.printStackTrace()
            isLoadingDialogVisible.postValue(false)
        }
    }

    fun createMeal(meal: CreateMealDTO) = viewModelScope.launch {
        try {
            mealsRepository.addMeal(meal)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun find3rdPartyMeals(query: String) = viewModelScope.launch {
        try {
            mealsRepository.find3rdPartyMeals(query)
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun findMealByBarcode(barcode: String) = viewModelScope.launch {
        try {
            mealsRepository.findBarcodeMeal(barcode)
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        } catch (e: BadRequestException) {
            foundBarcodeMeal.postValue(null)
        }
    }
}