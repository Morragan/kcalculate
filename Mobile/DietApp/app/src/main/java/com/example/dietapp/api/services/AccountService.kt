package com.example.dietapp.api.services

import com.example.dietapp.models.dto.LoginDTO
import com.example.dietapp.models.dto.RegisterDTO
import com.example.dietapp.models.dto.TokenDTO
import com.example.dietapp.models.dto.UserDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AccountService {
    @GET("account/check-logged-in")
    suspend fun pingAPI(): Response<Unit>

    @POST("account/login")
    suspend fun login(@Body credentials: LoginDTO): Response<TokenDTO>

    @POST("account/register")
    suspend fun register(@Body userData: RegisterDTO): Response<Unit>

    @POST("account/refresh-token")
    @FormUrlEncoded
    suspend fun refreshToken(
        @Field("accessToken") accessToken: String,
        @Field("refreshToken") refreshToken: String
    ): Response<TokenDTO>

    @POST("account/refresh-token")
    @FormUrlEncoded
    fun refreshTokenCall(
        @Field("accessToken") accessToken: String,
        @Field("refreshToken") refreshToken: String
    ): Call<TokenDTO>

    @GET("account")
    suspend fun getUserData(): Response<UserDTO>
}