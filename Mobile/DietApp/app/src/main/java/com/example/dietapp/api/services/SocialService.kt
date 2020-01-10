package com.example.dietapp.api.services

import com.example.dietapp.models.dto.FriendsDTO
import com.example.dietapp.models.dto.UserFoundDTO
import retrofit2.Response
import retrofit2.http.*

interface SocialService {
    @GET("social/friends")
    suspend fun getFriends(): Response<FriendsDTO>

    @FormUrlEncoded
    @POST("social/friends")
    suspend fun requestFriend(@Field("friendID") friendID: Int): Response<FriendsDTO>

    @PUT("social/accept-friend")
    suspend fun acceptFriend(@Field("friendID") friendID: Int): Response<FriendsDTO>

    @DELETE("social/friends")
    suspend fun deleteFriend(@Field("friendID") friendID: Int): Response<FriendsDTO>

    @POST("social/block-user")
    suspend fun blockUser(@Field("userID") userID: Int): Response<FriendsDTO>

    @GET("social/search")
    suspend fun searchPeople(@Query("nickname") nickname: String): Response<List<UserFoundDTO>>
}