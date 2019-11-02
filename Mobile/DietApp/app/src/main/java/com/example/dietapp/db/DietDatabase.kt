package com.example.dietapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dietapp.db.dao.FriendDao
import com.example.dietapp.models.MergedFriend

@Database(entities = [MergedFriend::class], version = 1)
abstract class DietDatabase : RoomDatabase(){
    abstract fun friendDao(): FriendDao
}