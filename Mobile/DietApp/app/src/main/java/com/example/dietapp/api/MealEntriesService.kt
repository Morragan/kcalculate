package com.example.dietapp.api

import com.example.dietapp.models.MealEntryDTO
import com.example.dietapp.models.RecordMealDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MealEntriesService {
    @GET("mealentries/history")
    fun getMealsHistory(): Call<List<MealEntryDTO>>

    @POST("mealentries")
    fun recordMeal(@Body mealEntry: RecordMealDTO): Call<List<MealEntryDTO>>
}