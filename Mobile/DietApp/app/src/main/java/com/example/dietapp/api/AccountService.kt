package com.example.dietapp.api

import com.example.dietapp.models.LoginDTO
import com.example.dietapp.models.TokenDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountService {
    @POST("account/login")
    fun login(@Body credentials: LoginDTO): Call<TokenDTO>
}