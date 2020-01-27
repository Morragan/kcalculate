package com.example.dietapp.models.dto

import com.example.dietapp.models.Nutrients

data class CreatePublicMealDTO(val name: String, val Nutrients: Nutrients, val barcode: String)