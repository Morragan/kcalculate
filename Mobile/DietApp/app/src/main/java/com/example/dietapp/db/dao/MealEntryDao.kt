package com.example.dietapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dietapp.models.entity.MealEntry

@Dao
interface MealEntryDao {
    @Query("select * from meal_entry order by date asc")
    fun getAll(): LiveData<MutableList<MealEntry>>

    @Insert
    fun insert(mealEntry: MealEntry)

    @Insert
    fun insertAll(mealEntries: List<MealEntry>)

    @Query("delete from meal_entry")
    fun deleteAll()
}