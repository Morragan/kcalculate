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
        nutrientGoalsData.kcalGoal,
        nutrientGoalsData.kcalLower,
        nutrientGoalsData.kcalUpper,
        nutrientGoalsData.carbsGoal,
        nutrientGoalsData.carbsLower,
        nutrientGoalsData.carbsUpper,
        nutrientGoalsData.fatGoal,
        nutrientGoalsData.fatLower,
        nutrientGoalsData.fatUpper,
        nutrientGoalsData.proteinGoal,
        nutrientGoalsData.proteinLower,
        nutrientGoalsData.proteinUpper
        )
}