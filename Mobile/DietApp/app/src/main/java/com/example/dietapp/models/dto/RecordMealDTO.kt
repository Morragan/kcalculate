package com.example.dietapp.models.dto

import com.example.dietapp.models.Nutrients

data class RecordMealDTO(val name: String, val nutrients: Nutrients, val weightGram: Int)