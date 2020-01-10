package com.example.dietapp.db.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.dietapp.api.ApiRequestHandler
import com.example.dietapp.api.services.MealsService
import com.example.dietapp.db.dao.MealDao
import com.example.dietapp.models.dto.CreateMealDTO
import com.example.dietapp.models.entity.Meal
import com.example.dietapp.utils.addItem
import com.example.dietapp.utils.removeItem
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

    suspend fun fetchMeals() {
        val mealsResponse = apiRequestHandler.executeRequest(mealsService::getUserMeals)
        mealsResponse.data?.let {
            val meals = it.map { mealDTO -> mealDTO.toMeal() }
            mealDao.deleteAll()
            mealDao.insertAll(meals)
        }
    }

    suspend fun addMeal(meal: CreateMealDTO) {
        val processedMeal = Meal(0, meal.name, meal.nutrients, true)
        mealDao.insert(processedMeal)
        val mealsResponse = apiRequestHandler.executeRequest(mealsService::createMeal, meal)
        mealDao.delete(processedMeal)
        mealsResponse.data?.let {
            val meals = it.map { meal -> meal.toMeal() }
            mealDao.deleteAll()
            mealDao.insertAll(meals)
        }
    }

    suspend fun find3rdPartyMeals(query: String): Meal {
        TODO()
    }
}