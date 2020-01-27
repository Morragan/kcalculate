package com.example.dietapp.models.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dietapp.models.Nutrients
import com.example.dietapp.models.dto.CreateMealDTO

@Entity(tableName = "meal")
data class Meal(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    @Embedded val nutrients: Nutrients
){
    fun toCreateMealDTO() = CreateMealDTO(name, nutrients)
}