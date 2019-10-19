package com.example.dietapp.api

import com.example.dietapp.models.LoginDTO
import com.example.dietapp.models.RegisterDTO
import com.example.dietapp.models.TokenDTO
import com.example.dietapp.models.UserDTO
import retrofit2.Call
import retrofit2.http.*

interface AccountService {
    @GET("account/check-logged-in")
    fun checkLoggedIn(): Call<Unit>

    @POST("account/login")
    fun login(@Body credentials: LoginDTO): Call<TokenDTO>

    @POST("account/register")
    fun register(@Body userData: RegisterDTO): Call<Unit>

    @POST("account/refresh-token")
    @FormUrlEncoded
    fun refreshToken(
        @Field("accessToken") accessToken: String,
        @Field("refreshToken") refreshToken: String
    ): Call<TokenDTO>

    @GET("account")
    fun getUserData(): Call<UserDTO>
}