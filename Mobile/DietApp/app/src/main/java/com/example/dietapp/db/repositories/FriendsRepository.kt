package com.example.dietapp.db.repositories

import androidx.lifecycle.MutableLiveData
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
    val searchResults = MutableLiveData<List<Friend>>()

    suspend fun fetchFriends() {
        val friendsResponse = apiRequestHandler.executeRequest(socialService::getFriends)
        friendsResponse.data?.toFriendsList().let {
            friendDao.deleteAll()
            friendDao.insertAll(it!!)
        }
    }

    suspend fun sendFriendRequest(friend: Friend) {
        val friendsResponse =
            apiRequestHandler.executeRequest(socialService::requestFriend, friend.id)
        friendsResponse.data?.toFriendsList()?.let {
            searchResults.value = searchResults.value?.filter { element -> element.id != friend.id }
            friendDao.deleteAll()
            friendDao.insertAll(it)
        }
    }

    suspend fun acceptFriendRequest(friend: Friend) {
        val friendsResponse =
            apiRequestHandler.executeRequest(socialService::acceptFriend, friend.id)
        friendsResponse.data?.toFriendsList()?.let {
            searchResults.value = searchResults.value?.filter { element -> element.id != friend.id }
            friendDao.deleteAll()
            friendDao.insertAll(it)
        }
    }

    suspend fun blockUser(user: Friend) {
        val friendsResponse =
            apiRequestHandler.executeRequest(socialService::blockUser, user.id)
        friendsResponse.data?.toFriendsList()?.let {
            searchResults.value = searchResults.value?.filter { element -> element.id != user.id }
            friendDao.deleteAll()
            friendDao.insertAll(it)
        }
    }


    suspend fun deleteFriend(friend: Friend) {
        val friendsResponse =
            apiRequestHandler.executeRequest(socialService::deleteFriend, friend.id)
        friendsResponse.data?.toFriendsList()?.let {
            searchResults.value = searchResults.value?.filter { element -> element.id != friend.id }
            friendDao.deleteAll()
            friendDao.insertAll(it)
        }
    }

    suspend fun searchUsers(query: String) {
        val usersResponse = apiRequestHandler.executeRequest(socialService::searchPeople, query)
        usersResponse.data?.let {
            searchResults.postValue(it.map { user -> user.toFriend() })
        }
    }
}