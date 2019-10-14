package com.example.dietapp.models

data class RegisterDTO(
    val email: String,
    val nickname: String,
    val password: String,
    val avatarLink: String,
    val calorieLimit: Int
)