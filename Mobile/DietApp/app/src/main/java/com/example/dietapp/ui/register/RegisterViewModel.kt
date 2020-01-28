package com.example.dietapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.db.repositories.AccountRepository
import com.example.dietapp.models.dto.RegisterDTO
import com.example.dietapp.ui.calculatenutrientgoals.NutrientGoalsData
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private val accountRepository: AccountRepository) :
    ViewModel() {
    val isRegistered = MutableLiveData<Boolean>(false)
    val isLoadingFragmentLoading = MutableLiveData<Boolean>()

    lateinit var nutrientGoalsData: NutrientGoalsData
    lateinit var email: String
    lateinit var nickname: String
    lateinit var password: String
    lateinit var avatarLink: String

    fun register() = viewModelScope.launch {

        val userData = RegisterDTO(
            email,
            nickname,
            password,
            avatarLink,
            nutrientGoalsData.kcalLower,
            nutrientGoalsData.kcalGoal,
            nutrientGoalsData.kcalUpper,
            nutrientGoalsData.carbsLower,
            nutrientGoalsData.carbsGoal,
            nutrientGoalsData.carbsUpper,
            nutrientGoalsData.fatLower,
            nutrientGoalsData.fatGoal,
            nutrientGoalsData.fatUpper,
            nutrientGoalsData.proteinLower,
            nutrientGoalsData.proteinGoal,
            nutrientGoalsData.proteinUpper
        )

        val isSuccessful = accountRepository.register(userData)
        isRegistered.value = isSuccessful
    }

    fun checkNicknameAvailability() = viewModelScope.launch {

    }
}