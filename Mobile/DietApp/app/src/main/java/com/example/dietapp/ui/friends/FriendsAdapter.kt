package com.example.dietapp.ui.friends

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dietapp.R
import com.example.dietapp.models.entity.Friend
import java.util.*

class FriendsAdapter(
    private val context: Context,
    private val acceptedOnClickListener: AcceptedOnClickListener,
    private val pendingOnClickListener: PendingOnClickListener,
    private val blockedOnClickListener: BlockedOnClickListener,
    private val userFoundOnClickListener: UserFoundOnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private val friends = mutableListOf<Friend>()
    private val filteredFriends = mutableListOf<Friend>()

    fun replaceUsers(users: List<Friend>) {
        friends.clear()
        filteredFriends.clear()
        friends.addAll(users)
        filteredFriends.addAll(users)
        notifyDataSetChanged()
    }

    override fun getFilter() = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<Friend>()
            if (constraint.isNullOrBlank())
                filteredList.addAll(friends)
            else {
                val pattern = constraint.toString().toLowerCase(Locale.getDefault()).trim()
                for (item: Friend in friends) {
                    if (item.nickname.toLowerCase(Locale.getDefault()).contains(pattern))
                        filteredList.add(item)
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredFriends.clear()
            @Suppress("UNCHECKED_CAST")
            filteredFriends.addAll(results?.values as Collection<Friend>)
            this@FriendsAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_friend_accepted, parent, false)
                return AcceptedViewHolder(view)
            }
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_friend_sent, parent, false)
                return SentViewHolder(view)
            }
            2 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_friend_pending, parent, false)
                return PendingViewHolder(view)
            }
            3 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_user_blocked, parent, false)
                return BlockedViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_user_found, parent, false)
                return UserFoundViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredFriends.size
    }

    override fun onBindViewHolder(_holder: RecyclerView.ViewHolder, position: Int) {
        val friend = filteredFriends[position]
        when (_holder.itemViewType) {
            0 -> {
                val holder = _holder as AcceptedViewHolder
                if (!friend.avatarLink.isNullOrBlank())
                    Glide.with(context).load(friend.avatarLink).into(holder.image)
                holder.nickname.text = friend.nickname
                holder.unfriendButton.setOnClickListener {
                    acceptedOnClickListener.onUnfriendClick(friend)
                }
                holder.blockButton.setOnClickListener {
                    acceptedOnClickListener.onBlockClick(friend)
                }
            }
            1 -> {
                val holder = _holder as SentViewHolder
                if (!friend.avatarLink.isNullOrBlank())
                    Glide.with(context).load(friend.avatarLink).into(holder.image)
                holder.nickname.text = friend.nickname
            }
            2 -> {
                val holder = _holder as PendingViewHolder
                if (!friend.avatarLink.isNullOrBlank())
                    Glide.with(context).load(friend.avatarLink).into(holder.image)
                holder.nickname.text = friend.nickname
                holder.acceptButton.setOnClickListener {
                    pendingOnClickListener.onAcceptClick(friend)
                }
                holder.rejectButton.setOnClickListener {
                    pendingOnClickListener.onRejectClick(friend)
                }
            }
            3 -> {
                val holder = _holder as BlockedViewHolder
                if (!friend.avatarLink.isNullOrBlank())
                    Glide.with(context).load(friend.avatarLink).into(holder.image)
                holder.nickname.text = friend.nickname
                holder.unblockButton.setOnClickListener {
                    blockedOnClickListener.onUnblockClick(friend)
                }
            }
            4 -> {
                val holder = _holder as UserFoundViewHolder
                if (!friend.avatarLink.isNullOrBlank())
                    Glide.with(context).load(friend.avatarLink).into(holder.image)
                holder.nickname.text = friend.nickname
                holder.befriendButton.setOnClickListener {
                    userFoundOnClickListener.onBefriendClick(friend)
                }
                holder.blockButton.setOnClickListener {
                    userFoundOnClickListener.onBlockClick(friend)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val friend = filteredFriends[position]
        return when {
            // Accepted
            friend.status == 2 -> 0
            // Sent
            friend.status == 1 && friend.isUserRequester -> 1
            // Pending
            friend.status == 1 && !friend.isUserRequester -> 2
            // Found
            friend.status == 0 -> 4
            // Blocked
            else -> 3
        }
    }

    class AcceptedViewHolder(rootItemView: View) : RecyclerView.ViewHolder(rootItemView) {
        internal val nickname =
            rootItemView.findViewById<TextView>(R.id.friends_list_item_accepted_nickname)
        internal val image =
            rootItemView.findViewById<ImageView>(R.id.friends_list_item_accepted_image)
        internal val unfriendButton =
            rootItemView.findViewById<Button>(R.id.friends_list_item_accepted_button_unfriend)
        internal val blockButton =
            rootItemView.findViewById<Button>(R.id.friends_list_item_accepted_button_block)

    }

    class SentViewHolder(rootItemView: View) : RecyclerView.ViewHolder(rootItemView) {
        internal val nickname =
            rootItemView.findViewById<TextView>(R.id.friends_list_item_sent_nickname)
        internal val image = rootItemView.findViewById<ImageView>(R.id.friends_list_item_sent_image)
    }

    class PendingViewHolder(rootItemView: View) : RecyclerView.ViewHolder(rootItemView) {
        internal val nickname =
            rootItemView.findViewById<TextView>(R.id.friends_list_item_pending_nickname)
        internal val image =
            rootItemView.findViewById<ImageView>(R.id.friends_list_item_pending_image)
        internal val acceptButton =
            rootItemView.findViewById<Button>(R.id.friends_list_item_pending_button_accept)
        internal val rejectButton =
            rootItemView.findViewById<Button>(R.id.friends_list_item_pending_button_reject)
    }

    class BlockedViewHolder(rootItemView: View) : RecyclerView.ViewHolder(rootItemView) {
        internal val nickname =
            rootItemView.findViewById<TextView>(R.id.friends_list_item_blocked_nickname)
        internal val image =
            rootItemView.findViewById<ImageView>(R.id.friends_list_item_blocked_image)
        internal val unblockButton =
            rootItemView.findViewById<Button>(R.id.friends_list_item_blocked_button_unblock)
    }

    class UserFoundViewHolder(rootItemView: View) : RecyclerView.ViewHolder(rootItemView) {
        internal val nickname =
            rootItemView.findViewById<TextView>(R.id.friends_list_item_found_nickname)
        internal val image =
            rootItemView.findViewById<ImageView>(R.id.friends_list_item_found_image)
        internal val befriendButton =
            rootItemView.findViewById<Button>(R.id.friends_list_item_found_button_befriend)
        internal val blockButton =
            rootItemView.findViewById<Button>(R.id.friends_list_item_found_button_block)
    }

    interface AcceptedOnClickListener {
        fun onUnfriendClick(friend: Friend)
        fun onBlockClick(user: Friend)
    }

    interface PendingOnClickListener {
        fun onAcceptClick(friend: Friend)
        fun onRejectClick(friend: Friend)
    }

    interface BlockedOnClickListener {
        fun onUnblockClick(user: Friend)
    }

    interface UserFoundOnClickListener {
        fun onBefriendClick(user: Friend)
        fun onBlockClick(user: Friend)
    }
}