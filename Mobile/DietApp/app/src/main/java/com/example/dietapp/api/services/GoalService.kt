package com.example.dietapp.api.services

import com.example.dietapp.models.dto.CreateGoalDTO
import com.example.dietapp.models.entity.Goal
import retrofit2.Response
import retrofit2.http.*

interface GoalService {
    @GET("goals")
    suspend fun getGoal(): Response<Goal>

    @POST("goals")
    suspend fun createGoal(@Body goal: CreateGoalDTO): Response<Goal>

    @PUT("goals/accept")
    suspend fun acceptGoal(): Response<Goal>

    @DELETE("goals/remove")
    suspend fun removeGoal(): Response<Unit>
}