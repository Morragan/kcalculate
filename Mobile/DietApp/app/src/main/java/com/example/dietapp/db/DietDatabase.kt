package com.example.dietapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dietapp.db.dao.FriendDao
import com.example.dietapp.db.dao.MealDao
import com.example.dietapp.db.dao.MealEntryDao
import com.example.dietapp.models.entity.Friend
import com.example.dietapp.models.entity.Meal
import com.example.dietapp.models.entity.MealEntry

@Database(
    entities = [Friend::class, Meal::class, MealEntry::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class DietDatabase : RoomDatabase() {
    abstract fun friendDao(): FriendDao
    abstract fun mealDao(): MealDao
    abstract fun mealEntryDao(): MealEntryDao
}