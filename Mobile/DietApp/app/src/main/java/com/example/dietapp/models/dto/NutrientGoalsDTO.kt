package com.example.dietapp.models.dto

import com.example.dietapp.ui.calculatenutrientgoals.NutrientGoalsData

data class NutrientGoalsDTO(
    val calorieLimit: Int,
    val calorieLimitLower: Int,
    val calorieLimitUpper: Int,
    val carbsLimit: Int,
    val carbsLimitLower: Int,
    val carbsLimitUpper: Int,
    val fatLimit: Int,
    val fatLimitLower: Int,
    val fatLimitUpper: Int,
    val proteinLimit: Int,
    val proteinLimitLower: Int,
    val proteinLimitUpper: Int
) {
    constructor(nutrientGoalsData: NutrientGoalsData) : this(
        nutrientGoalsData.kcalLower,
        nutrientGoalsData.kcalGoal,
        nutrientGoalsData.kcalUpper,
        nutrientGoalsData.carbsLower,
        nutrientGoalsData.carbsGoal,
        nutrientGoalsData.carbsUpper,
        nutrientGoalsData.fatLower,
        nutrientGoalsData.fatGoal,
        nutrientGoalsData.fatUpper,
        nutrientGoalsData.proteinLower,
        nutrientGoalsData.proteinGoal,
        nutrientGoalsData.proteinUpper,
        )
}