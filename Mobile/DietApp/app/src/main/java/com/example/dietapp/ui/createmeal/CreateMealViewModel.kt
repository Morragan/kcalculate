package com.example.dietapp.ui.createmeal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.api.exceptions.NotAuthorizedException
import com.example.dietapp.db.repositories.AccountRepository
import com.example.dietapp.db.repositories.MealsRepository
import com.example.dietapp.models.dto.CreateMealDTO
import com.example.dietapp.models.dto.CreatePublicMealDTO
import com.example.dietapp.utils.ButtonState
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateMealViewModel @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val accountRepository: AccountRepository
) :
    ViewModel() {

    val buttonState = MutableLiveData<ButtonState>(ButtonState.DEFAULT)
    val loggedIn = accountRepository.loggedIn
    val isSuccess = MutableLiveData<Boolean>()

    fun createMeal(meal: CreateMealDTO) = viewModelScope.launch {
        buttonState.value = ButtonState.LOADING
        try {
            mealsRepository.addMeal(meal)
            isSuccess.postValue(true)
        } catch (e: NotAuthorizedException) {
            isSuccess.postValue(false)
            accountRepository.logout()
        } catch (e: Exception) {
            isSuccess.postValue(false)
            e.printStackTrace()
        }
    }

    fun createPublicMeal(publicMeal: CreatePublicMealDTO) = viewModelScope.launch {
        buttonState.value = ButtonState.LOADING
        try {
            mealsRepository.addPublicMeal(publicMeal)
            isSuccess.postValue(true)
        } catch (e: NotAuthorizedException) {
            isSuccess.postValue(false)
            accountRepository.logout()
        } catch (e: Exception) {
            isSuccess.postValue(false)
            e.printStackTrace()
        }
    }
}