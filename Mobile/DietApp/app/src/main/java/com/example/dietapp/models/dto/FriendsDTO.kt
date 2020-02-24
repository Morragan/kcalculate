package com.example.dietapp.models.dto

import com.example.dietapp.models.entity.Friend

data class FriendsDTO(val requestedFriends: List<FriendDTO>, val receivedFriends: List<FriendDTO>){
    fun toFriendsList(): List<Friend>{
        val friends: MutableList<Friend> = mutableListOf()

        val requested = requestedFriends.map { friend ->
            Friend(
                friend.id,
                friend.nickname,
                friend.avatarLink,
                friend.points,
                friend.goalPoints,
                friend.status,
                isUserRequester = true
            )
        }
        val received = receivedFriends.map { friend ->
            Friend(
                friend.id,
                friend.nickname,
                friend.avatarLink,
                friend.points,
                friend.goalPoints,
                friend.status,
                isUserRequester = false
            )
        }

        friends.addAll(requested)
        friends.addAll(received)

        return friends
    }
}