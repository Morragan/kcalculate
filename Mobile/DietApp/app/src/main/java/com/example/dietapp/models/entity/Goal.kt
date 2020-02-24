package com.example.dietapp.models.entity

import java.util.*

data class Goal(
    val calorieLimit: Float,
    val calorieLimitLower: Float,
    val calorieLimitUpper: Float,
    val carbsLimit: Float,
    val carbsLimitLower: Float,
    val carbsLimitUpper: Float,
    val fatLimit: Float,
    val fatLimitLower: Float,
    val fatLimitUpper: Float,
    val proteinLimit: Float,
    val proteinLimitLower: Float,
    val proteinLimitUpper: Float,
    val goalID: Int,
    val participatingFriends: List<Int>,
    val startDate: Date,
    val status: Int,
    val weightGoal: Float
)