package com.example.dietapp.models

import androidx.room.ColumnInfo

data class Nutrients(
    @ColumnInfo(name = "carbs_100") val carbs: Double,
    @ColumnInfo(name = "fat_100") val fat: Double,
    @ColumnInfo(name = "protein_100") val protein: Double,
    @ColumnInfo(name = "kcal_100") val kcal: Double
)