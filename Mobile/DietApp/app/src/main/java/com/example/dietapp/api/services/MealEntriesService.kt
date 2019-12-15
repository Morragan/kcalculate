package com.example.dietapp.api.services

import com.example.dietapp.models.dto.MealEntryDTO
import com.example.dietapp.models.dto.RecordMealDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MealEntriesService {
    @GET("mealentries/history")
    suspend fun getMealsHistory(): Response<List<MealEntryDTO>>

    @POST("mealentries")
    suspend fun recordMeal(@Body mealEntry: RecordMealDTO): Response<List<MealEntryDTO>>
}