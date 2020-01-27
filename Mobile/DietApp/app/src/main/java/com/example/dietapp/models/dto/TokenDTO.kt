package com.example.dietapp.models.dto

import java.util.*

data class TokenDTO(val accessToken: String, val refreshToken: String, val expiration: Long) {
    fun isExpired() = expiration <= Date().time
}