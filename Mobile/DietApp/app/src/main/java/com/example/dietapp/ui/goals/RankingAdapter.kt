package com.example.dietapp.ui.goals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dietapp.R
import com.example.dietapp.models.entity.Friend

class RankingAdapter(private val context: Context) : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    val friends = mutableListOf<Friend>()

    fun replaceFriends(newFriends: List<Friend>) {
        friends.clear()
        friends.addAll(newFriends)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_friend_ranking, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = friends.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friends[position]

        holder.place.text = (position + 1).toString()
        holder.nickname.text = friend.nickname
        holder.points.text = context.getString(R.string.goals_ranking_points, friend.goalPoints)
        if (!friend.avatarLink.isNullOrBlank())
            Glide.with(context).load(friend.avatarLink).into(holder.image)
    }

    class ViewHolder(rootItemView: View) : RecyclerView.ViewHolder(rootItemView) {
        val place: TextView = rootItemView.findViewById(R.id.friends_ranking_place)
        val image: ImageView= rootItemView.findViewById(R.id.friends_ranking_image)
        val nickname: TextView = rootItemView.findViewById(R.id.friends_ranking_nickname)
        val points: TextView = rootItemView.findViewById(R.id.friends_ranking_points)
    }
}