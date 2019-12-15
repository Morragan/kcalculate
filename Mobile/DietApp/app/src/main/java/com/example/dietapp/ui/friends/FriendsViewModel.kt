package com.example.dietapp.ui.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.db.repositories.FriendsRepository
import com.example.dietapp.models.dto.UserFoundDTO
import kotlinx.coroutines.launch
import javax.inject.Inject

class FriendsViewModel @Inject constructor(private val friendsRepository: FriendsRepository) :
    ViewModel() {

    val allFriends = friendsRepository.allFriends

    fun fetchFriends() = viewModelScope.launch {
        friendsRepository.fetchFriends()
    }

    fun sendFriendRequest(user: UserFoundDTO) = viewModelScope.launch {

    }

    fun acceptFriendRequest(friendId: Int) = viewModelScope.launch {

    }

    fun deleteUser(userId: Int) = viewModelScope.launch {

    }

    fun blockUser(userId: Int) = viewModelScope.launch {

    }

    fun searchPeople(query: String) = viewModelScope.launch {

    }
}