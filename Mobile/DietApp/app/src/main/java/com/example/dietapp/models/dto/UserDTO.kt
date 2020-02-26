package com.example.dietapp.models.dto

import com.example.dietapp.models.entity.User
import java.util.*

data class UserDTO(
    val id: Int,
    val nickname: String,
    val email: String,
    val avatarLink: String,
    val joinDate: Date,
    val points: Int,
    val goalPoints: Int,
    val isEmailConfirmed: Boolean,
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
    val isPrivate: Boolean
) {
    fun toUser() = User(
        id,
        nickname,
        email,
        avatarLink,
        joinDate,
        points,
        goalPoints,
        isEmailConfirmed,
        calorieLimit,
        calorieLimitLower,
        calorieLimitUpper,
        carbsLimit,
        carbsLimitLower,
        carbsLimitUpper,
        fatLimit,
        fatLimitLower,
        fatLimitUpper,
        proteinLimit,
        proteinLimitLower,
        proteinLimitUpper,
        isPrivate
    )
}