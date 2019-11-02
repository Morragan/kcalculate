package com.example.dietapp.ui.register

import com.example.dietapp.api.AccountService
import com.example.dietapp.di.scopes.ActivityScope
import com.example.dietapp.models.RegisterDTO
import com.example.dietapp.ui.base.BasePresenter
import com.example.dietapp.utils.Enums
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@ActivityScope
class RegisterPresenter @Inject constructor(private val accountService: AccountService) :
    BasePresenter<RegisterView>() {

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

        mvpView?.showCalorieLimit(
            calorieLimit.toInt(),
            carbsLimit.toInt(),
            fatLimit.toInt(),
            proteinLimit.toInt()
        )
    }

    fun onRegisterButtonClick(
        email: String,
        nickname: String,
        password: String,
        avatarLink: String,
        calorieLimit: Int
    ) {

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
        accountService.register(userData).enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                mvpView?.showConnectionFailure()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (!response.isSuccessful) {
                    mvpView?.showErrorMessage(response.errorBody()!!.string())
                    return
                }
                mvpView?.startLoginActivity()
            }

        })
    }

}