package com.example.dietapp.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friend")
data class Friend(
    @PrimaryKey val id: Int,
    val nickname: String,
    @ColumnInfo(name = "avatar_link") val avatarLink: String?,
    val points: Int,
    val status: Int,
    @ColumnInfo(name = "is_user_requester") val isUserRequester: Boolean
)