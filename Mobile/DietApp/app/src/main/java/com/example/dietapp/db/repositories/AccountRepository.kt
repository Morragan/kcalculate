package com.example.dietapp.db.repositories

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.example.dietapp.api.ApiRequestHandler
import com.example.dietapp.api.services.AccountService
import com.example.dietapp.models.dto.LoginDTO
import com.example.dietapp.models.dto.RegisterDTO
import com.example.dietapp.models.entity.User
import com.example.dietapp.utils.getToken
import com.example.dietapp.utils.getUser
import com.example.dietapp.utils.saveToken
import com.example.dietapp.utils.saveUser
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val apiRequestHandler: ApiRequestHandler,
    private val accountService: AccountService,
    private val sharedPreferences: SharedPreferences
) {
    val loggedIn = MutableLiveData<Boolean>(false)
    val user = MutableLiveData<User>(sharedPreferences.getUser())

    suspend fun register(userData: RegisterDTO): Boolean {
        val registerResponse = apiRequestHandler.executeRequest(accountService::register, userData)
        registerResponse.exception?.let { throw it }
        return registerResponse.isSuccessful
    }

    suspend fun login(credentials: LoginDTO) {
        val loginResponse = apiRequestHandler.executeRequest(accountService::login, credentials)
        loginResponse.data?.let {
            sharedPreferences.saveToken(it)
        }
        loginResponse.exception?.let { throw it }
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
        userResponse.exception?.let { throw it }
    }

    fun getUserData() = sharedPreferences.getUser()
}