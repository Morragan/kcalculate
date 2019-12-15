package com.example.dietapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.db.repositories.AccountRepository
import com.example.dietapp.models.dto.RegisterDTO
import com.example.dietapp.utils.Enums
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private val accountRepository: AccountRepository) :
    ViewModel() {
    val isRegistered = MutableLiveData<Boolean>(false)
    val nutrientLimits = MutableLiveData<NutrientLimit>()

    fun register(
        email: String,
        nickname: String,
        password: String,
        avatarLink: String,
        calorieLimit: Int
    ) = viewModelScope.launch {

        val calorieLimitLower = calorieLimit * 0.95
        val calorieLimitUpper = calorieLimit * 1.05

        val kcalInCarbsGram = 4
        val kcalInFatGram = 9
        val kcalInProteinGram = 4

        val carbsLimit = calorieLimit * 0.5 / kcalInCarbsGram
        val carbsLimitLower = calorieLimit * 0.45 / kcalInCarbsGram
        val carbsLimitUpper = calorieLimit * 0.65 / kcalInCarbsGram

        val fatLimit = calorieLimit * 0.25 / kcalInFatGram
        val fatLimitLower = calorieLimit * 0.2 / kcalInFatGram
        val fatLimitUpper = calorieLimit * 0.35 / kcalInFatGram

        val proteinLimit = calorieLimit * 0.25 / kcalInProteinGram
        val proteinLimitLower = calorieLimit * 0.1 / kcalInProteinGram
        val proteinLimitUpper = calorieLimit * 0.35 / kcalInProteinGram

        val userData = RegisterDTO(
            email,
            nickname,
            password,
            avatarLink,
            calorieLimit,
            calorieLimitLower.toInt(),
            calorieLimitUpper.toInt(),
            carbsLimit.toInt(),
            carbsLimitLower.toInt(),
            carbsLimitUpper.toInt(),
            fatLimit.toInt(),
            fatLimitLower.toInt(),
            fatLimitUpper.toInt(),
            proteinLimit.toInt(),
            proteinLimitLower.toInt(),
            proteinLimitUpper.toInt()
        )

        val isSuccessful = accountRepository.register(userData)
        isRegistered.value = isSuccessful
    }


    fun calculateCalorieLimit(
        heightCm: Float,
        weightKg: Float,
        gender: Enums.Gender,
        weightGoal: Enums.WeightGoal,
        activityLevel: Enums.ActivityLevel
    ) {
        // Mifflin/St Jeor equation
        val bmr =
            10 * weightKg + 6.25 * heightCm - 5 * 20 - if (gender == Enums.Gender.Male) -5 else 161

        val calorieLimit = bmr * when (activityLevel) {
            Enums.ActivityLevel.VeryLow -> 1.3
            Enums.ActivityLevel.Low -> 1.55
            Enums.ActivityLevel.Moderate -> 1.65
            Enums.ActivityLevel.High -> 1.8
            else -> 2.0
        } * when (weightGoal) {
            Enums.WeightGoal.Lose -> 0.85
            Enums.WeightGoal.Maintain -> 1.0
            else -> 1.15
        }

        val kcalInCarbsGram = 4
        val kcalInFatGram = 9
        val kcalInProteinGram = 4

        val carbsLimit = calorieLimit * 0.5 / kcalInCarbsGram

        val fatLimit = calorieLimit * 0.25 / kcalInFatGram

        val proteinLimit = calorieLimit * 0.25 / kcalInProteinGram

        nutrientLimits.value = NutrientLimit(
            calorieLimit.toInt(),
            carbsLimit.toInt(),
            fatLimit.toInt(),
            proteinLimit.toInt()
        )
    }

    data class NutrientLimit(val calorieLimit: Int, val carbsLimit: Int, val  fatLimit: Int, val proteinLimit:Int)
}