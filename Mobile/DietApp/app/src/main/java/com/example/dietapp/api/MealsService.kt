package com.example.dietapp.api

import com.example.dietapp.models.MealDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MealsService {
    @POST("meals")
    fun createMeal(@Body meal: MealDTO): Call<List<MealDTO>>

    @GET("meals")
    fun getUserMeals(): Call<List<MealDTO>>
}