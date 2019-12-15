package com.example.dietapp.models.dto

import com.example.dietapp.models.Nutrients
import com.example.dietapp.models.entity.Meal

data class MealDTO(
    val id: Int,
    val name: String,
    val nutrients: Nutrients
) {
    fun toMeal() = Meal(id, name, nutrients)
}