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
        val loginResponse = apiRequestHandler.executeRequest(accountService::login, credentials)
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
        apiRequestHandler.executeRequest(accountService::pingAPI)
        loggedIn.value = true
    }

    suspend fun fetchUserData() {
        val userResponse = apiRequestHandler.executeRequest(accountService::getUserData)
        userResponse.data?.let {
            val storedUser = sharedPreferences.getUser()
            val receivedUser = it.toUser()
            if(receivedUser != storedUser){
                sharedPreferences.saveUser(it)
                user.postValue(receivedUser)
            }
        }
    }
}