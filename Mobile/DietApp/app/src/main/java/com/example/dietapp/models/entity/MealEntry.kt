package com.example.dietapp.models.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dietapp.models.Nutrients
import java.util.*

@Entity(tableName = "meal_entry")
data class MealEntry(
    @PrimaryKey val id: Int,
    val name: String,
    val date: Date,
    @Embedded val nutrients: Nutrients,
    @ColumnInfo(name = "weight_gram") val weightGram: Int,
    val kcal: Double,
    val carbs: Double,
    val fat: Double,
    val protein: Double
)