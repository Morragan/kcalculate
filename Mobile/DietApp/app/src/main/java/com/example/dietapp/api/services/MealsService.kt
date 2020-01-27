package com.example.dietapp.api.services

import com.example.dietapp.models.dto.CreateMealDTO
import com.example.dietapp.models.dto.CreatePublicMealDTO
import com.example.dietapp.models.dto.MealDTO
import retrofit2.Response
import retrofit2.http.*

interface MealsService {
    @POST("meals")
    suspend fun createMeal(@Body meal: CreateMealDTO): Response<List<MealDTO>>

    @GET("meals")
    suspend fun getUserMeals(): Response<List<MealDTO>>

    @GET("meals/meal-name/{name}")
    suspend fun searchMealsByName(@Path("name", encoded = true) name: String): Response<List<MealDTO>>

    @GET("meals/meal-barcode/{barcode}")
    suspend fun searchMealByBarcode(@Path("barcode", encoded = true) barcode: String): Response<MealDTO>

    @POST("meals/public")
    suspend fun createPublicMeal(@Body publicMeal: CreatePublicMealDTO): Response<List<MealDTO>>
}