package com.example.dietapp.models.dto

import com.example.dietapp.models.entity.Friend

data class UserFoundDTO(val id: Int, val nickname: String, val avatarLink: String) {
    fun toFriend() = Friend(
        id, nickname, avatarLink, 0, 0,
        isUserRequester = false,
        isLoading = false
    )
}