package com.example.dietapp.models

import java.util.*

data class MealEntryDTO(val name: String, val date: Date, val nutrients: Nutrients, val weightGram: Int, val kcal: Int)