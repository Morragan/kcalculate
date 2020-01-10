package com.example.dietapp.db.repositories

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.example.dietapp.api.ApiRequestHandler
import com.example.dietapp.api.ApiResponse
import com.example.dietapp.api.services.AccountService
import com.example.dietapp.models.dto.LoginDTO
import com.example.dietapp.models.dto.RegisterDTO
import com.example.dietapp.models.dto.TokenDTO
import com.example.dietapp.models.entity.User
import com.example.dietapp.utils.*
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val apiRequestHandler: ApiRequestHandler,
    private val accountService: AccountService,
    private val sharedPreferences: SharedPreferences
) {
    val loggedIn = MutableLiveData<Boolean>()
    val user = MutableLiveData<User>(sharedPreferences.getUser())

    suspend fun register(userData: RegisterDTO): Boolean {
        val registerResponse = apiRequestHandler.executeRequest(accountService::register, userData)
        return registerResponse.isSuccessful
    }

    suspend fun login(credentials: LoginDTO) {
        val res = accountService.login(credentials)
        val loginResponse = ApiResponse(data = res.body(), isSuccessful = true)
        loginResponse.data?.let {
            sharedPreferences.saveToken(it)
            loggedIn.postValue(true)
        }
    }

    fun logout(){
        sharedPreferences.removeUser()
        sharedPreferences.removeToken()
        loggedIn.value = false
    }

    suspend fun checkUserLoggedIn() {
        val token = sharedPreferences.getToken()
        if (token.accessToken.isBlank()) {
            loggedIn.value = false
            return
        }
        val loggedInResponse = apiRequestHandler.executeRequest(accountService::pingAPI)
        loggedIn.value = loggedInResponse.isSuccessful
    }

    suspend fun fetchUserData() {
        val userResponse = apiRequestHandler.executeRequest(accountService::getUserData)
        userResponse.data?.let { sharedPreferences.saveUser(it) }
    }

    fun getUserData() = sharedPreferences.getUser()
}