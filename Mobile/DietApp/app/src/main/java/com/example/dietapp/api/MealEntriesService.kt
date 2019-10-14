package com.example.dietapp.api

import com.example.dietapp.models.MealEntryDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MealEntriesService {
    @GET
    fun getMealsHistory(): Call<List<MealEntryDTO>>
}