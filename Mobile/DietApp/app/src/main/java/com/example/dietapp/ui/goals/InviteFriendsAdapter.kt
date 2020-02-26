package com.example.dietapp.ui.goals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.models.entity.Friend

class InviteFriendsAdapter(private val onFriendCheckedListener: OnFriendCheckedListener) :
    RecyclerView.Adapter<InviteFriendsAdapter.ViewHolder>() {

    val friends = mutableListOf<Friend>()

    fun replaceFriends(newFriends: List<Friend>){
        friends.clear()
        friends.addAll(newFriends)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_friend_invite, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = friends.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friends[position]

        holder.nickname.text = friend.nickname
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) onFriendCheckedListener.onFriendChecked(friend.id)
            else onFriendCheckedListener.onFriendUncheckedListener(friend.id)
        }
    }

    class ViewHolder(rootItemView: View) : RecyclerView.ViewHolder(rootItemView) {
        val checkBox: CheckBox = rootItemView.findViewById(R.id.invite_friends_dialog_checkbox)
        val nickname: TextView = rootItemView.findViewById(R.id.invite_friends_dialog_nickname)
    }

    interface OnFriendCheckedListener {
        fun onFriendChecked(friendId: Int)
        fun onFriendUncheckedListener(friendId: Int)
    }
}