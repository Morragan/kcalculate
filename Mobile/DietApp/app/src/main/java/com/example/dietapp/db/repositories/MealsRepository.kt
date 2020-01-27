package com.example.dietapp.db.repositories

import androidx.lifecycle.MutableLiveData
import com.example.dietapp.api.ApiRequestHandler
import com.example.dietapp.api.services.MealsService
import com.example.dietapp.db.dao.MealDao
import com.example.dietapp.models.dto.CreateMealDTO
import com.example.dietapp.models.dto.CreatePublicMealDTO
import com.example.dietapp.models.entity.Meal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsRepository @Inject constructor(
    private val apiRequestHandler: ApiRequestHandler,
    private val mealDao: MealDao,
    private val mealsService: MealsService
) {
    val allMeals = mealDao.getAll()
    val found3rdPartyMeals = MutableLiveData<List<Meal>>()
    val foundBarcodeMeal = MutableLiveData<Meal?>()

    suspend fun fetchMeals() {
        val mealsResponse = apiRequestHandler.executeRequest(mealsService::getUserMeals)
        mealsResponse.data?.let {
            val meals = it.map { mealDTO -> mealDTO.toMeal() }
            mealDao.deleteAll()
            mealDao.insertAll(meals)
        }
    }

    suspend fun addMeal(meal: CreateMealDTO) {
        val mealsResponse = apiRequestHandler.executeRequest(mealsService::createMeal, meal)
        mealsResponse.data?.let {
            val meals = it.map { meal -> meal.toMeal() }
            mealDao.deleteAll()
            mealDao.insertAll(meals)
        }
    }

    suspend fun find3rdPartyMeals(query: String) {
        val mealsResponse = apiRequestHandler.executeRequest(mealsService::searchMealsByName, query)
        mealsResponse.data?.let {
            val meals = it.map { meal -> meal.toMeal() }
            found3rdPartyMeals.postValue(meals)
        }
    }

    suspend fun findBarcodeMeal(barcode: String) {
        val mealResponse =
            apiRequestHandler.executeRequest(mealsService::searchMealByBarcode, barcode)
        mealResponse.data?.let {
            foundBarcodeMeal.postValue(it.toMeal())
        }
    }

    suspend fun addPublicMeal(publicMeal: CreatePublicMealDTO) {
        val mealsResponse =
            apiRequestHandler.executeRequest(mealsService::createPublicMeal, publicMeal)
        mealsResponse.data?.let {
            val meals = it.map { mealDTO -> mealDTO.toMeal() }
            mealDao.deleteAll()
            mealDao.insertAll(meals)
        }
    }
}