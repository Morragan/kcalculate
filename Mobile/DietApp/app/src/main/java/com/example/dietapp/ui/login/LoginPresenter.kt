package com.example.dietapp.ui.login

import android.app.Application
import android.content.SharedPreferences
import com.example.dietapp.R
import com.example.dietapp.api.AccountService
import com.example.dietapp.models.LoginDTO
import com.example.dietapp.models.TokenDTO
import com.example.dietapp.ui.base.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val accountService: AccountService,
    private val sharedPreferences: SharedPreferences,
    private val app: Application
) :
    BasePresenter<LoginView>() {

    fun onLoginButtonClick(nickname: String, password: String) {
        val credentials = LoginDTO(nickname, password)
        accountService.login(credentials).enqueue(object : Callback<TokenDTO> {
            override fun onFailure(call: Call<TokenDTO>, t: Throwable) {
                mvpView?.showConnectionFailure()
            }

            override fun onResponse(call: Call<TokenDTO>, response: Response<TokenDTO>) {
                if (!response.isSuccessful) {
                    mvpView?.showErrorMessage(response.message())
                    return
                }
                val token = response.body()!!
                with(sharedPreferences.edit()) {
                    putString(
                        app.resources.getString(R.string.preference_access_token),
                        token.accessToken
                    )
                    putString(
                        app.resources.getString(R.string.preference_refresh_token),
                        token.refreshToken
                    )
                    putLong(
                        app.resources.getString(R.string.preference_token_expiration),
                        token.expiration
                    )
                    commit()
                }

                mvpView?.startHomeActivity()
            }

        })
    }
}