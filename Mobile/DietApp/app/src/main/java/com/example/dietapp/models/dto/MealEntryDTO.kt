package com.example.dietapp.models.dto

import com.example.dietapp.models.Nutrients
import com.example.dietapp.models.entity.MealEntry
import java.util.*

data class MealEntryDTO(
    val id: Int,
    val name: String,
    val date: Date,
    val nutrients: Nutrients,
    val weightGram: Int,
    val kcal: Double,
    val carbs: Double,
    val fat: Double,
    val protein: Double
) {
    fun toMealEntry() = MealEntry(id, name, date, nutrients, weightGram, kcal, carbs, fat, protein)
}