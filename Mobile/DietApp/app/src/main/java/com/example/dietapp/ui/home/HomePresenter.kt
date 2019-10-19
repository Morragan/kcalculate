package com.example.dietapp.ui.home

import android.content.SharedPreferences
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.api.AccountService
import com.example.dietapp.api.MealEntriesService
import com.example.dietapp.di.scopes.ActivityScope
import com.example.dietapp.models.MealEntryDTO
import com.example.dietapp.models.TokenDTO
import com.example.dietapp.models.UserDTO
import com.example.dietapp.ui.base.BasePresenter
import com.example.dietapp.utils.Constants
import com.example.dietapp.utils.Methods
import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.combine.combine
import nl.komponents.kovenant.deferred
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

@ActivityScope
class HomePresenter @Inject constructor(
    private val mealEntriesService: MealEntriesService,
    private val accountService: AccountService,
    private val sharedPreferences: SharedPreferences
) :
    BasePresenter<HomeView>() {

    private var date = Date()

    fun onDateButtonBackward() {
        date = Methods.offsetDate(date, -1)
        mvpView?.changeDay(date)
        updateView()
    }

    fun onDateButtonForward() {
        date = Methods.offsetDate(date, 1)
        mvpView?.changeDay(date)
        updateView()
    }

    fun loadData() {
        combine(getMealEntries(), getUserData()).success {
            val mealEntriesResponse = it.first
            val userResponse = it.second
            if (!mealEntriesResponse.isSuccessful || !userResponse.isSuccessful) {
                if (mealEntriesResponse.code() == 401 || userResponse.code() == 401)
                    tryRefreshToken()

                return@success
            }
            DietApp.mealEntries = mealEntriesResponse.body()
            DietApp.user = userResponse.body()

            updateView()
        }
    }

    private fun updateView() {
        if (DietApp.mealEntries == null || DietApp.user == null) return

        val selectedDateEntries = DietApp.mealEntries!!.filter { entry ->
            Methods.datesAreTheSameDay(date, entry.date)
        }

        val kcal = selectedDateEntries.sumBy { entry -> entry.kcal }
        val carbs = selectedDateEntries.sumBy { entry -> entry.nutrients.carbsGram }
        val fats = selectedDateEntries.sumBy { entry -> entry.nutrients.fatGram }
        val proteins = selectedDateEntries.sumBy { entry -> entry.nutrients.proteinGram }

        val kcalProgress = kcal.toFloat() / DietApp.user!!.calorieLimit * 100f
        val carbsProgress = carbs.toFloat() / DietApp.user!!.carbsLimit * 100f
        val fatProgress = fats.toFloat() / DietApp.user!!.fatLimit * 100f
        val proteinProgress = proteins.toFloat() / DietApp.user!!.proteinLimit * 100f

        val kcalProgressColorId = when {
            kcal < DietApp.user!!.calorieLimitLower -> R.color.warning
            kcal > DietApp.user!!.calorieLimitUpper -> R.color.error
            else -> R.color.success
        }
        val carbsProgressColorId = when {
            carbs < DietApp.user!!.carbsLimitLower -> R.color.warning
            carbs > DietApp.user!!.carbsLimitUpper -> R.color.error
            else -> R.color.success
        }
        val fatProgressColorId = when {
            fats < DietApp.user!!.fatLimitLower -> R.color.warning
            fats > DietApp.user!!.fatLimitUpper -> R.color.error
            else -> R.color.success
        }
        val proteinProgressColorId = when {
            proteins < DietApp.user!!.proteinLimitLower -> R.color.warning
            proteins > DietApp.user!!.proteinLimitUpper -> R.color.error
            else -> R.color.success
        }

        mvpView?.updateKcalProgress(
            kcalProgress,
            DietApp.user!!.calorieLimit,
            kcal,
            DietApp.user!!.calorieLimit - kcal,
            kcalProgressColorId
        )
        mvpView?.updateCarbsProgress(
            carbsProgress,
            DietApp.user!!.carbsLimit,
            carbs,
            DietApp.user!!.carbsLimit - carbs,
            carbsProgressColorId
        )
        mvpView?.updateFatProgress(
            fatProgress,
            DietApp.user!!.fatLimit,
            fats,
            DietApp.user!!.fatLimit - fats,
            fatProgressColorId
        )
        mvpView?.updateProteinProgress(
            proteinProgress,
            DietApp.user!!.proteinLimit,
            proteins,
            DietApp.user!!.proteinLimit - proteins,
            proteinProgressColorId
        )
    }


    private fun getMealEntries(): Promise<Response<List<MealEntryDTO>>, Throwable> {
        val deferred = deferred<Response<List<MealEntryDTO>>, Throwable>()
        mealEntriesService.getMealsHistory().enqueue(object : Callback<List<MealEntryDTO>> {
            override fun onFailure(call: Call<List<MealEntryDTO>>, t: Throwable) =
                deferred.reject(t)

            override fun onResponse(
                call: Call<List<MealEntryDTO>>,
                response: Response<List<MealEntryDTO>>
            ) = deferred.resolve(response)
        })
        return deferred.promise
    }

    private fun getUserData(): Promise<Response<UserDTO>, Throwable> {
        val deferred = deferred<Response<UserDTO>, Throwable>()
        accountService.getUserData().enqueue(object : Callback<UserDTO> {
            override fun onFailure(call: Call<UserDTO>, t: Throwable) = deferred.reject(t)
            override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) =
                deferred.resolve(response)
        })
        return deferred.promise
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
                }
                val token = response.body()!!
                with(sharedPreferences.edit()) {
                    putString(Constants.sharedPreferencesKeyAccessToken, token.accessToken)
                    putString(Constants.sharedPreferencesKeyRefreshToken, token.refreshToken)
                    putLong(Constants.sharedPreferencesKeyTokenExpiration, token.expiration)
                    commit()
                }
                mvpView?.logout()
            }
        })
    }

}