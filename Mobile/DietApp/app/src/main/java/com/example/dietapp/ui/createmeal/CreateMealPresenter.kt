package com.example.dietapp.ui.createmeal

import android.content.SharedPreferences
import com.example.dietapp.api.AccountService
import com.example.dietapp.api.MealsService
import com.example.dietapp.di.scopes.ActivityScope
import com.example.dietapp.models.MealDTO
import com.example.dietapp.models.Nutrients
import com.example.dietapp.models.TokenDTO
import com.example.dietapp.ui.base.BasePresenter
import com.example.dietapp.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@ActivityScope
class CreateMealPresenter @Inject constructor(
    private val mealsService: MealsService,
    private val accountService: AccountService,
    private val sharedPreferences: SharedPreferences
) :
    BasePresenter<CreateMealView>() {

    fun addMeal(name: String, nutrients: Nutrients) {
        mealsService.createMeal(MealDTO(name, nutrients)).enqueue(object : Callback<List<MealDTO>> {
            override fun onFailure(call: Call<List<MealDTO>>, t: Throwable) {
                mvpView?.showConnectionError()
            }

            override fun onResponse(call: Call<List<MealDTO>>, response: Response<List<MealDTO>>) {
                if (!response.isSuccessful) {
                    if (response.code() == 401) tryRefreshToken()
                    mvpView?.logout()
                }
                mvpView?.onSuccess()
            }
        })
    }

    private fun tryRefreshToken() {
        val accessToken =
            sharedPreferences.getString(Constants.sharedPreferencesKeyAccessToken, "")!!
        val refreshToken =
            sharedPreferences.getString(Constants.sharedPreferencesKeyRefreshToken, "")!!

        accountService.refreshToken(accessToken, refreshToken).enqueue(object : Callback<TokenDTO> {
            override fun onFailure(call: Call<TokenDTO>, t: Throwable) {
                with(sharedPreferences.edit()) {
                    remove(Constants.sharedPreferencesKeyAccessToken)
                    remove(Constants.sharedPreferencesKeyRefreshToken)
                    remove(Constants.sharedPreferencesKeyTokenExpiration)
                    apply()
                }
                mvpView?.logout()
            }

            override fun onResponse(call: Call<TokenDTO>, response: Response<TokenDTO>) {
                if (!response.isSuccessful) {
                    with(sharedPreferences.edit()) {
                        remove(Constants.sharedPreferencesKeyAccessToken)
                        remove(Constants.sharedPreferencesKeyRefreshToken)
                        remove(Constants.sharedPreferencesKeyTokenExpiration)
                        apply()
                    }
                    mvpView?.logout()
                    return
                }
                val token = response.body()!!
                with(sharedPreferences.edit()) {
                    putString(
                        Constants.sharedPreferencesKeyAccessToken,
                        token.accessToken
                    )
                    putString(
                        Constants.sharedPreferencesKeyRefreshToken,
                        token.refreshToken
                    )
                    putLong(
                        Constants.sharedPreferencesKeyTokenExpiration,
                        token.expiration
                    )
                    commit()
                }
                mvpView?.logout()
            }
        })
    }
}