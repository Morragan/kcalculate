package com.example.dietapp.ui.calculatenutrientgoals

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietapp.utils.Enums
import javax.inject.Inject

class CalculateNutrientGoalsViewModel @Inject constructor() : ViewModel() {
    private val userMeasurementsData = MutableLiveData<UserMeasurementsData>(
        UserMeasurementsData(
            1f,
            1f,
            Enums.Gender.Male,
            Enums.WeightGoal.Maintain,
            Enums.ActivityLevel.Low
        )
    )
    private val result = MediatorLiveData<NutrientGoalsData>()

    val kcalLow = MediatorLiveData<String>().apply { addSource(result) { this.value = it.kcalLower.toString() } }
    val kcalGoal = MediatorLiveData<String>().apply { addSource(result) { this.value = it.kcalGoal.toString() } }
    val kcalUpper = MediatorLiveData<String>().apply { addSource(result) { this.value = it.kcalUpper.toString() } }

    val carbsLow = MediatorLiveData<String>().apply { addSource(result) { this.value = it.carbsLower.toString() } }
    val carbsGoal = MediatorLiveData<String>().apply { addSource(result) { this.value = it.carbsGoal.toString() } }
    val carbsUpper = MediatorLiveData<String>().apply { addSource(result) { this.value = it.carbsUpper.toString() } }

    val fatLow = MediatorLiveData<String>().apply { addSource(result) { this.value = it.fatLower.toString() } }
    val fatGoal = MediatorLiveData<String>().apply { addSource(result) { this.value = it.fatGoal.toString() } }
    val fatUpper = MediatorLiveData<String>().apply { addSource(result) { this.value = it.fatUpper.toString() } }

    val proteinLow = MediatorLiveData<String>().apply { addSource(result) { this.value = it.proteinLower.toString() } }
    val proteinGoal = MediatorLiveData<String>().apply { addSource(result) { this.value = it.proteinGoal.toString() } }
    val proteinUpper = MediatorLiveData<String>().apply { addSource(result) { this.value = it.proteinUpper.toString() } }

    val nutrientGoals
        get() = NutrientGoalsData(
            kcalLow.value!!.toInt(),
            kcalGoal.value!!.toInt(),
            kcalUpper.value!!.toInt(),
            carbsLow.value!!.toInt(),
            carbsGoal.value!!.toInt(),
            carbsUpper.value!!.toInt(),
            fatLow.value!!.toInt(),
            fatGoal.value!!.toInt(),
            fatUpper.value!!.toInt(),
            proteinLow.value!!.toInt(),
            proteinGoal.value!!.toInt(),
            proteinUpper.value!!.toInt()
        )

    init {
        result.addSource(userMeasurementsData) {
            val (heightCm, weightKg, gender, weightGoal, activityLevel) = it

            // Mifflin/St Jeor equation
            val bmr = //TODO: check age
                10 * weightKg + 6.25f * heightCm - 5 * 20 - if (gender == Enums.Gender.Male) -5 else 161

            val calorieLimit = bmr * when (activityLevel) {
                Enums.ActivityLevel.VeryLow -> 1.3f
                Enums.ActivityLevel.Low -> 1.55f
                Enums.ActivityLevel.Moderate -> 1.65f
                Enums.ActivityLevel.High -> 1.8f
                else -> 2.0f
            } * when (weightGoal) {
                Enums.WeightGoal.Lose -> 0.85f
                Enums.WeightGoal.Maintain -> 1.0f
                else -> 1.15f
            }
            val calorieLimitLower = calorieLimit * 0.95f
            val calorieLimitUpper = calorieLimit * 1.05f

            val kcalInCarbsGram = 4
            val kcalInFatGram = 9
            val kcalInProteinGram = 4

            val carbsLimit = calorieLimit * 0.5f / kcalInCarbsGram
            val carbsLimitLower = calorieLimit * 0.45f / kcalInCarbsGram
            val carbsLimitUpper = calorieLimit * 0.65f / kcalInCarbsGram

            val fatLimit = calorieLimit * 0.25f / kcalInFatGram
            val fatLimitLower = calorieLimit * 0.2f / kcalInFatGram
            val fatLimitUpper = calorieLimit * 0.35f / kcalInFatGram

            val proteinLimit = calorieLimit * 0.25f / kcalInProteinGram
            val proteinLimitLower = calorieLimit * 0.1f / kcalInProteinGram
            val proteinLimitUpper = calorieLimit * 0.35f / kcalInProteinGram

            result.value = NutrientGoalsData(
                calorieLimitLower.toInt(),
                calorieLimit.toInt(),
                calorieLimitUpper.toInt(),

                carbsLimitLower.toInt(),
                carbsLimit.toInt(),
                carbsLimitUpper.toInt(),

                fatLimitLower.toInt(),
                fatLimit.toInt(),
                fatLimitUpper.toInt(),

                proteinLimitLower.toInt(),
                proteinLimit.toInt(),
                proteinLimitUpper.toInt()
            )
        }
    }

    fun setMeasurements(heightCm: Float, weightKg: Float, gender: Enums.Gender) {
        val measurements = userMeasurementsData.value!!
        measurements.heightCm = heightCm
        measurements.weightKg = weightKg
        measurements.gender = gender
        userMeasurementsData.value = measurements
    }

    fun setQuizData(weightGoal: Enums.WeightGoal, activityLevel: Enums.ActivityLevel) {
        val measurements = userMeasurementsData.value!!
        measurements.weightGoal = weightGoal
        measurements.activityLevel = activityLevel
        userMeasurementsData.value = measurements
    }
}