package com.example.dietapp.models.dto

data class FriendDTO(
    val id: Int,
    val nickname: String,
    val avatarLink: String?,
    val points: Int,
    val goalPoints: Int,
    val status: Int
)