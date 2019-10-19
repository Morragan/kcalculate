package com.example.dietapp.ui.home

import java.util.*

interface HomeView {
    fun showConnectionError()
    fun logout()
    fun changeDay(date: Date)
    fun updateKcalProgress(progress: Float, goal: Int, eaten: Int, left: Int, progressBarColorId: Int)
    fun updateCarbsProgress(progress: Float, goal: Int, eaten: Int, left: Int, progressBarColorId: Int)
    fun updateFatProgress(progress: Float, goal: Int, eaten: Int, left: Int, progressBarColorId: Int)
    fun updateProteinProgress(progress: Float, goal: Int, eaten: Int, left: Int, progressBarColorId: Int)
}