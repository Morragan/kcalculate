package com.example.dietapp.ui.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.api.exceptions.NotAuthorizedException
import com.example.dietapp.db.repositories.AccountRepository
import com.example.dietapp.db.repositories.FriendsRepository
import com.example.dietapp.models.dto.UserFoundDTO
import com.example.dietapp.models.entity.Friend
import kotlinx.coroutines.launch
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
    private val friendsRepository: FriendsRepository,
    private val accountRepository: AccountRepository
) :
    ViewModel() {

    val allFriends = friendsRepository.allFriends
    val userSearchResults = friendsRepository.searchResults
    val loggedIn = accountRepository.loggedIn

    fun fetchFriends() = viewModelScope.launch {
        try {
            friendsRepository.fetchFriends()
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        }
    }

    fun sendFriendRequest(user: Friend) = viewModelScope.launch {
        friendsRepository.sendFriendRequest(user)
    }

    fun acceptFriendRequest(friend: Friend) = viewModelScope.launch {
        friendsRepository.acceptFriendRequest(friend)
    }

    fun deleteUser(user: Friend) = viewModelScope.launch {
        friendsRepository.deleteFriend(user)
    }

    fun blockUser(user: Friend) = viewModelScope.launch {
        friendsRepository.blockUser(user)
    }

    fun searchPeople(query: String) = viewModelScope.launch {
        friendsRepository.searchUsers(query)
    }
}