package com.example.dietapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dietapp.models.entity.MealEntry

@Dao
interface MealEntryDao {
    @Query("select * from meal_entry order by date asc")
    fun getAll(): LiveData<List<MealEntry>>

    @Insert
    suspend fun insert(mealEntry: MealEntry)

    @Insert
    suspend fun insertAll(mealEntries: List<MealEntry>)

    @Query("delete from meal_entry")
    suspend fun deleteAll()
}