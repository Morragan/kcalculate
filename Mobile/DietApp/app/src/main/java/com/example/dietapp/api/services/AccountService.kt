package com.example.dietapp.api.services

import com.example.dietapp.models.dto.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AccountService {
    @GET("account/check-logged-in")
    suspend fun pingAPI(): Response<Unit>

    @POST("account/login")
    suspend fun login(@Body credentials: LoginDTO): Response<TokenDTO>

    @POST("account/register")
    suspend fun register(@Body userData: RegisterDTO): Response<Unit>

    @POST("account/refresh-token")
    suspend fun refreshToken(@Body tokensDTO: RefreshTokenDTO): Response<TokenDTO>

    @POST("account/refresh-token")
    fun refreshTokenCall(@Body tokensDTO: RefreshTokenDTO): Call<TokenDTO>

    @GET("account")
    suspend fun getUserData(): Response<UserDTO>

    @PUT("account/privacy")
    suspend fun changeAccountIsPrivate(@Body isPrivateDTO: ChangeAccountPrivacyDTO): Response<UserDTO>

    @PUT
    suspend fun changeUserNutrientGoals(@Body nutrientGoalsDTO: NutrientGoalsDTO): Response<UserDTO>
}