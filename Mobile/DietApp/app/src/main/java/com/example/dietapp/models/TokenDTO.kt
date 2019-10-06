package com.example.dietapp.models

data class TokenDTO(val accessToken: String, val refreshToken: String, val expiration: Long)