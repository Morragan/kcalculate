package com.example.dietapp.ui.main

import android.content.SharedPreferences
import com.example.dietapp.api.AccountService
import com.example.dietapp.di.scopes.ActivityScope
import com.example.dietapp.models.TokenDTO
import com.example.dietapp.ui.base.BasePresenter
import com.example.dietapp.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@ActivityScope
class MainPresenter @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val accountService: AccountService
) : BasePresenter<MainView>() {

    fun checkUserLoggedIn(){
        val accessToken = sharedPreferences.getString(Constants.sharedPreferencesKeyAccessToken, null)
        if (accessToken == null) {
            mvpView?.logout()
            return
        }

        accountService.checkLoggedIn().enqueue(object: Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                mvpView?.showConnectionError()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(!response.isSuccessful){
                    if(response.code() == 401) tryRefreshToken()

                    mvpView?.logout()
                }
                mvpView?.login()
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