package com.example.dietapp.api.services

import com.example.dietapp.models.dto.CreateMealDTO
import com.example.dietapp.models.dto.MealDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MealsService {
    @POST("meals")
    suspend fun createMeal(@Body meal: CreateMealDTO): Response<List<MealDTO>>

    @GET("meals")
    suspend fun getUserMeals(): Response<List<MealDTO>>
}