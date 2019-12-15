package com.example.dietapp.ui.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.models.entity.Friend
import java.util.*

class FriendsAdapter(
    private val acceptedOnClickListener: AcceptedOnClickListener,
    private val pendingOnClickListener: PendingOnClickListener,
    private val blockedOnClickListener: BlockedOnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    fun replaceUsers(users: List<Friend>) {
        DietApp.friends.clear()
        DietApp.filteredFriends.clear()
        DietApp.friends.addAll(users)
        DietApp.filteredFriends.addAll(users)
        notifyDataSetChanged()
    }

    override fun getFilter() = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<Friend>()
            if (constraint.isNullOrBlank())
                filteredList.addAll(DietApp.friends)
            else {
                val pattern = constraint.toString().toLowerCase(Locale.getDefault()).trim()
                for (item: Friend in DietApp.friends) {
                    if (item.nickname.toLowerCase(Locale.getDefault()).contains(pattern))
                        filteredList.add(item)
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            DietApp.filteredFriends.clear()
            @Suppress("UNCHECKED_CAST")
            DietApp.filteredFriends.addAll(results?.values as Collection<Friend>)
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
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_user_blocked, parent, false)
                return BlockedViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return DietApp.filteredFriends.size
    }

    override fun onBindViewHolder(_holder: RecyclerView.ViewHolder, position: Int) {
        val friend = DietApp.filteredFriends[position]
        when (_holder.itemViewType) {
            0 -> {
                val holder = _holder as AcceptedViewHolder
                holder.nickname.text = friend.nickname
                holder.unfriendButton.setOnClickListener{
                    acceptedOnClickListener.onUnfriendClick(friend.id)
                }
                holder.blockButton.setOnClickListener {
                    acceptedOnClickListener.onBlockClick(friend.id)
                }
            }
            1 -> {
                val holder = _holder as SentViewHolder
                holder.nickname.text = friend.nickname
            }
            2 -> {
                val holder = _holder as PendingViewHolder
                holder.nickname.text = friend.nickname
                holder.acceptButton.setOnClickListener {
                    pendingOnClickListener.onAcceptClick(friend.id)
                }
                holder.rejectButton.setOnClickListener {
                    pendingOnClickListener.onRejectClick(friend.id)
                }
            }
            3 -> {
                val holder = _holder as BlockedViewHolder
                holder.nickname.text = friend.nickname
                holder.unblockButton.setOnClickListener {
                    blockedOnClickListener.onUnblockClick(friend.id)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val friend = DietApp.filteredFriends[position]
        return when {
            // Accepted
            friend.status == 2 -> 0
            // Sent
            friend.status == 1 && friend.isUserRequester -> 1
            // Pending
            friend.status == 1 && !friend.isUserRequester -> 2
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
            rootItemView.findViewById<TextView>(R.id.friends_list_item_sent_nickname)
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

    interface AcceptedOnClickListener {
        fun onUnfriendClick(friendId: Int)
        fun onBlockClick(userId: Int)
    }

    interface PendingOnClickListener {
        fun onAcceptClick(friendId: Int)
        fun onRejectClick(friendId: Int)
    }

    interface BlockedOnClickListener {
        fun onUnblockClick(userId: Int)
    }
}