package com.example.dietapp.models.entity

import java.util.Date

data class User(
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
    fun toFriend() = Friend(id, nickname, avatarLink, points, goalPoints, 2, true)
}