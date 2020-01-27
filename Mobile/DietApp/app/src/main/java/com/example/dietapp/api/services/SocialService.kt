package com.example.dietapp.api.services

import com.example.dietapp.models.dto.FriendsDTO
import com.example.dietapp.models.dto.UserFoundDTO
import retrofit2.Response
import retrofit2.http.*

interface SocialService {
    @GET("social/friends")
    suspend fun getFriends(): Response<FriendsDTO>

    @POST("social/friends/{friend_id}")
    suspend fun requestFriend(@Path("friend_id", encoded = true) friendID: Int): Response<FriendsDTO>

    @PUT("social/accept-friend/{friend_id}")
    suspend fun acceptFriend(@Path("friend_id", encoded = true) friendID: Int): Response<FriendsDTO>

    @DELETE("social/friends/{friend_id}")
    suspend fun deleteFriend(@Path("friend_id", encoded = true) friendID: Int): Response<FriendsDTO>

    @POST("social/block-user/{user_id}")
    suspend fun blockUser(@Path("user_id", encoded = true) userID: Int): Response<FriendsDTO>

    @GET("social/search")
    suspend fun searchPeople(@Query("nickname") nickname: String): Response<List<UserFoundDTO>>
}