package com.example.dietapp.ui.friends

import com.example.dietapp.models.MergedFriend
import com.example.dietapp.models.SearchUserDTO

interface FriendsView {
    fun showConnectionError()
    fun logout()
    fun replaceFriends(friends: List<MergedFriend>)
    fun replaceUsers(users: List<SearchUserDTO>)
    fun showOperationResult(isSuccessful: Boolean)
}