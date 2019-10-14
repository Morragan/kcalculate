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
        activityLevel: Enums.ActivityLevel,
        fatPercentage: Enums.BodyFatPercentage
    ) {
        // TODO: actually calculate the limit
        mvpView?.showCalorieLimit(2000)
    }

    fun onRegisterButtonClick(
        email: String,
        nickname: String,
        password: String,
        avatarLink: String,
        calorieLimit: Int
    ) {
        val userData = RegisterDTO(email, nickname, password, avatarLink, calorieLimit)
        accountService.register(userData).enqueue(object: Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                mvpView?.showConnectionFailure()
                            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(!response.isSuccessful){
                    mvpView?.showErrorMessage(response.message())
                    return
                }
                mvpView?.startLoginActivity()
            }

        })
    }

}