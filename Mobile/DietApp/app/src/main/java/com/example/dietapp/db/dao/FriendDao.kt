package com.example.dietapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.dietapp.models.MergedFriend

@Dao
interface FriendDao {
    @Query("select * from friends order by id asc")
    fun getFriends(): LiveData<List<MergedFriend>>
}