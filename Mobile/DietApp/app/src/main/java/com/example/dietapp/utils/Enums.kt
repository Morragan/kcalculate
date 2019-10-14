package com.example.dietapp.utils

object Enums {
    enum class Gender {
        Male, Female
    }

    enum class WeightGoal{
        Lose, Maintain, Gain
    }

    enum class ActivityLevel{
        VeryLow, Low, Moderate, High, VeryHigh
    }

    enum class BodyFatPercentage{
        // From lowest to highest
        Class1, Class2, Class3, Class4, Class5, Class6
    }

    enum class HeightUnit{
        Centimeters, Feet
    }

    enum class WeightUnit{
        Kilograms, Pounds
    }
}