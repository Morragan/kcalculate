package com.example.dietapp.models

data class Friend(
    val id: Int,
    val nickname: String,
    val avatarLink: String?,
    val points: Int,
    val status: Int
)