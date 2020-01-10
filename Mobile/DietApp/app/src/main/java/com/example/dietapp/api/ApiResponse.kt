package com.example.dietapp.api

data class ApiResponse<T>(var data: T? = null, var isSuccessful: Boolean = false)