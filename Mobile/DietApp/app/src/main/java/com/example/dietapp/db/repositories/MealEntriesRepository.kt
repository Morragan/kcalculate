package com.example.dietapp.db.repositories

import com.example.dietapp.api.ApiRequestHandler
import com.example.dietapp.api.services.MealEntriesService
import com.example.dietapp.db.dao.MealEntryDao
import com.example.dietapp.models.dto.RecordMealDTO
import javax.inject.Inject

class MealEntriesRepository @Inject constructor(
    private val apiRequestHandler: ApiRequestHandler,
    private val mealEntryDao: MealEntryDao,
    private val mealEntriesService: MealEntriesService
) {
    val allMealEntries = mealEntryDao.getAll()

    suspend fun fetchMealEntries() {
        val mealEntriesResponse =
            apiRequestHandler.executeRequest(mealEntriesService::getMealsHistory)
        mealEntriesResponse.data?.let {
            val mealEntries = it.map { mealEntryDTO -> mealEntryDTO.toMealEntry() }
            mealEntryDao.deleteAll()
            mealEntryDao.insertAll(mealEntries)
        }
    }

    suspend fun recordMeal(meal: RecordMealDTO) {
        //TODO: if I do meal history, temporarily save item with isLoading
        val mealEntriesResponse =
            apiRequestHandler.executeRequest(mealEntriesService::recordMeal, meal)
        mealEntriesResponse.data?.let {
            val mealEntries = it.map { mealEntryDTO -> mealEntryDTO.toMealEntry() }
            mealEntryDao.deleteAll()
            mealEntryDao.insertAll(mealEntries)
        }
    }
}