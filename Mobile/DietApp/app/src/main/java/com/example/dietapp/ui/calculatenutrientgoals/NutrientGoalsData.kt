package com.example.dietapp.ui.calculatenutrientgoals

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NutrientGoalsData(
    val kcalLower: Int,
    val kcalGoal: Int,
    val kcalUpper: Int,

    val carbsLower: Int,
    val carbsGoal: Int,
    val carbsUpper: Int,

    val fatLower: Int,
    val fatGoal: Int,
    val fatUpper: Int,

    val proteinLower: Int,
    val proteinGoal: Int,
    val proteinUpper: Int
) : Parcelable