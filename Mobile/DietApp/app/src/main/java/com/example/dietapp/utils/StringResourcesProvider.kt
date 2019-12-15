package com.example.dietapp.utils

import android.app.Application
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringResourcesProvider @Inject constructor(private val application: Application) {
    fun getString(resId: Int): String = application.getString(resId)
}