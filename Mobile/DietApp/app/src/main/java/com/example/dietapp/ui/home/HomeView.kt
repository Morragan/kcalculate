package com.example.dietapp.ui.home

import android.net.Uri
import java.util.*

interface HomeView {
    fun showConnectionError()
    fun logout()
    fun changeDay(date: Date)
    fun updateProfile(name: String, email: String, avatarLink: Uri)
    fun updateKcalProgress(progress: Float, goal: Int, eaten: Int, left: Int, progressBarColorId: Int)
    fun updateCarbsProgress(progress: Int, goal: Int, eaten: Int, left: Int, progressBarColorId: Int)
    fun updateFatProgress(progress: Int, goal: Int, eaten: Int, left: Int, progressBarColorId: Int)
    fun updateProteinProgress(progress: Int, goal: Int, eaten: Int, left: Int, progressBarColorId: Int)
}