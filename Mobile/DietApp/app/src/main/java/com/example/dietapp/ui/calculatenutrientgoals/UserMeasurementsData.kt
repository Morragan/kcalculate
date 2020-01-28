package com.example.dietapp.ui.calculatenutrientgoals

import com.example.dietapp.utils.Enums

data class UserMeasurementsData(
    var heightCm: Float,
    var weightKg: Float,
    var gender: Enums.Gender,
    var weightGoal: Enums.WeightGoal,
    var activityLevel: Enums.ActivityLevel
)