package com.example.dietapp.db.repositories

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.example.dietapp.api.ApiRequestHandler
import com.example.dietapp.api.services.GoalService
import com.example.dietapp.models.dto.CreateGoalDTO
import com.example.dietapp.models.entity.Goal
import com.example.dietapp.utils.getGoal
import com.example.dietapp.utils.saveGoal
import javax.inject.Inject

class GoalRepository @Inject constructor(
    private val apiRequestHandler: ApiRequestHandler,
    private val goalService: GoalService,
    private val sharedPreferences: SharedPreferences
) {
    val goal = MutableLiveData<Goal>(sharedPreferences.getGoal())

    suspend fun getGoal() {
        val goalResponse = apiRequestHandler.executeRequest(goalService::getGoal)
        if (goalResponse.data != null) {
            sharedPreferences.saveGoal(goalResponse.data!!)
        }
        goal.postValue(goalResponse.data)
    }

    suspend fun createGoal(goalDTO: CreateGoalDTO) {
        val goalResponse = apiRequestHandler.executeRequest(goalService::createGoal, goalDTO)
        if (goalResponse.isSuccessful) {
            sharedPreferences.saveGoal(goalResponse.data!!)
            goal.postValue(goalResponse.data!!)
        }
    }

    suspend fun acceptGoal() {
        val goalResponse = apiRequestHandler.executeRequest(goalService::acceptGoal)
        sharedPreferences.saveGoal(goalResponse.data!!)
        goal.postValue(goalResponse.data!!)
    }

    suspend fun removeGoal() {
        val goalResponse = apiRequestHandler.executeRequest(goalService::removeGoal)
        if (goalResponse.isSuccessful) goal.postValue(null)
    }
}