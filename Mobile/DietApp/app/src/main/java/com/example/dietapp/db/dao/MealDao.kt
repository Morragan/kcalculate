package com.example.dietapp.db.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.dietapp.models.entity.Meal

@Dao
interface MealDao{
    @Query("select * from meal order by name asc")
    fun getAll(): LiveData<List<Meal>>

    @Insert
    suspend fun insert(meal: Meal)

    @Insert
    suspend fun insertAll(meals: List<Meal>)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("delete from meal")
    suspend fun deleteAll()
}