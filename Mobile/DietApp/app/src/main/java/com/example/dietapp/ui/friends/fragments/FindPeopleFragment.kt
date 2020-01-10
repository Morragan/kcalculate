package com.example.dietapp.ui.friends.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.ui.friends.FriendsAdapter

class FindPeopleFragment : BaseFriendsFragment() {

    companion object {
        @JvmStatic
        fun newInstance(_adapter: FriendsAdapter) =
            FindPeopleFragment().apply {
                adapter = _adapter
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_friends_people, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.friends_recycler_view_people)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        return view
    }


}
