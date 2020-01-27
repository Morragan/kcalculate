package com.example.dietapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dietapp.models.entity.Friend

@Dao
interface FriendDao {
    @Query("select * from friend order by nickname asc")
    fun getAll(): LiveData<List<Friend>>

    @Insert
    suspend fun insert(friend: Friend)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(friends: List<Friend>)

    @Delete
    suspend fun delete(friend: Friend)

    @Query("delete from friend")
    suspend fun deleteAll()
}