package com.example.dietapp.models

import androidx.room.ColumnInfo

data class Nutrients(
    val carbs: Double,
    val fat: Double,
    val protein: Double,
    @ColumnInfo(name = "kcal_100") val kcal: Double
)