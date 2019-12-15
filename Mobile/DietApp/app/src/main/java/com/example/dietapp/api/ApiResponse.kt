package com.example.dietapp.api

data class ApiResponse<T>(var exception: Exception? = null, var data: T? = null, var isSuccessful: Boolean = false)