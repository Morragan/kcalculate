package com.example.dietapp.ui.friends

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.api.exceptions.NotAuthorizedException
import com.example.dietapp.db.repositories.AccountRepository
import com.example.dietapp.db.repositories.FriendsRepository
import com.example.dietapp.models.entity.Friend
import com.example.dietapp.utils.ViewState
import com.example.dietapp.utils.ViewState.DEFAULT
import com.example.dietapp.utils.ViewState.LOADING
import kotlinx.coroutines.launch
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
    private val friendsRepository: FriendsRepository,
    private val accountRepository: AccountRepository
) :
    ViewModel() {

    private val allFriends = friendsRepository.allFriends
    val pendingFriends =
        Transformations.map(allFriends) { it.filter { friend -> friend.status == 1 } }
    val friends =
        Transformations.map(allFriends) { it.filter { friend -> friend.status == 2 } }
    val blockedUsers =
        Transformations.map(allFriends) { it.filter { friend -> friend.status == 3 } }
    val userSearchResults = friendsRepository.searchResults
    val loggedIn = accountRepository.loggedIn
    val user = accountRepository.user
    val pendingFragmentViewState = MutableLiveData<ViewState>()
    val friendsFragmentViewState = MutableLiveData<ViewState>()
    val blockedFragmentViewState = MutableLiveData<ViewState>()
    val searchFragmentViewState = MutableLiveData<ViewState>(DEFAULT)

    fun fetchFriends() = viewModelScope.launch {
        try {
            pendingFragmentViewState.value = LOADING
            friendsFragmentViewState.value = LOADING
            blockedFragmentViewState.value = LOADING
            friendsRepository.fetchFriends()
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun sendFriendRequest(user: Friend) = viewModelScope.launch {
        try {
            pendingFragmentViewState.value = LOADING
            friendsFragmentViewState.value = LOADING
            friendsRepository.sendFriendRequest(user)
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun acceptFriendRequest(friend: Friend) = viewModelScope.launch {
        try {
            pendingFragmentViewState.value = LOADING
            friendsFragmentViewState.value = LOADING
            friendsRepository.acceptFriendRequest(friend)
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteUser(user: Friend) = viewModelScope.launch {
        try {
            pendingFragmentViewState.value = LOADING
            friendsFragmentViewState.value = LOADING
            blockedFragmentViewState.value = LOADING
            friendsRepository.deleteFriend(user)
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun blockUser(user: Friend) = viewModelScope.launch {
        try {
            friendsFragmentViewState.value = LOADING
            blockedFragmentViewState.value = LOADING
            pendingFragmentViewState.value = LOADING
            friendsRepository.blockUser(user)
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun searchPeople(query: String) = viewModelScope.launch {
        try {
            searchFragmentViewState.value = LOADING
            friendsRepository.searchUsers(query)
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}