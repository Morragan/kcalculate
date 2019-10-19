package com.example.dietapp.models

import java.util.*

data class UserDTO(
    val id: Int,
    val nickname: String,
    val email: String,
    val avatarLink: String,
    val joinDate: Date,
    val points: Int,
    val isEmailConfirmed: Boolean,
    val calorieLimit: Int,
    val calorieLimitLower: Int,
    val calorieLimitUpper: Int,
    val carbsLimit: Int,
    val carbsLimitLower: Int,
    val carbsLimitUpper: Int,
    val fatLimit: Int,
    val fatLimitLower: Int,
    val fatLimitUpper: Int,
    val proteinLimit: Int,
    val proteinLimitLower: Int,
    val proteinLimitUpper: Int,
    val isPrivate: Boolean
)