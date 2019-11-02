package com.example.dietapp.api

import com.example.dietapp.models.FriendsDTO
import com.example.dietapp.models.SearchUserDTO
import retrofit2.Call
import retrofit2.http.*

interface SocialService {
    @GET("social/friends")
    fun getFriends(): Call<FriendsDTO>

    @FormUrlEncoded
    @POST("social/friends")
    fun requestFriend(@Field("friendID") friendID: Int): Call<FriendsDTO>

    @PUT("social/accept-friend")
    fun acceptFriend(@Field("friendID") friendID: Int): Call<FriendsDTO>

    @DELETE("social/friends")
    fun deleteFriend(@Field("friendID") friendID: Int): Call<FriendsDTO>

    @POST("social/block-user")
    fun blockUser(@Field("userID") userID: Int): Call<FriendsDTO>

    @GET("social/search")
    fun searchPeople(@Query("nickname") nickname: String): Call<List<SearchUserDTO>>
}