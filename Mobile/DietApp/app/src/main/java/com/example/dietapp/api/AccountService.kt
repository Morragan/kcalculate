package com.example.dietapp.api

import retrofit2.Call
import retrofit2.http.GET

interface AccountService {
    @GET("values")
    fun getValue(): Call<String>
}