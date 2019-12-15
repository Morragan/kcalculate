package com.example.dietapp.models.dto

import com.example.dietapp.models.Nutrients

data class CreateMealDTO(val name: String, val nutrients: Nutrients)