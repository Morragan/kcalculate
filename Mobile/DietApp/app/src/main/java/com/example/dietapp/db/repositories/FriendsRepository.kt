package com.example.dietapp.db.repositories

import com.example.dietapp.api.ApiRequestHandler
import com.example.dietapp.api.services.SocialService
import com.example.dietapp.db.dao.FriendDao
import com.example.dietapp.models.entity.Friend
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendsRepository @Inject constructor(
    private val friendDao: FriendDao,
    private val socialService: SocialService,
    private val apiRequestHandler: ApiRequestHandler
) {
    val allFriends = friendDao.getAll()

    suspend fun fetchFriends() {
        val friendsResponse = apiRequestHandler.executeRequest(socialService::getFriends)
        friendsResponse.data?.toFriendsList().let {
            friendDao.deleteAll()
            friendDao.insertAll(it!!)
        }
        friendsResponse.exception?.let { throw it }
    }

    suspend fun deleteFriend(friend: Friend) {
        val processedFriend = Friend(
            friend.id, friend.nickname, friend.avatarLink, friend.points, friend.status,
            isUserRequester = false,
            isLoading = true
        )
        friendDao.insert(processedFriend)
        val friendsResponse =
            apiRequestHandler.executeRequest(socialService::deleteFriend, friend.id)
        friendsResponse.data?.toFriendsList().let {
            friendDao.insertAll(it!!)
        }
        friendsResponse.exception?.let { throw it }
    }
}