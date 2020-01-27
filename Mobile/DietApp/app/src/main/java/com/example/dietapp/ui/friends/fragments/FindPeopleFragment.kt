package com.example.dietapp.ui.friends.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.ui.friends.FriendsAdapter
import com.example.dietapp.ui.friends.FriendsViewModel
import com.example.dietapp.utils.ViewState.*
import kotlinx.android.synthetic.main.fragment_friends_people.*

class FindPeopleFragment : BaseFriendsFragment() {

    companion object {
        @JvmStatic
        fun newInstance(_adapter: FriendsAdapter, _viewModel: FriendsViewModel) =
            FindPeopleFragment().apply {
                adapter = _adapter
                viewModel = _viewModel
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

        viewModel!!.userSearchResults.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                viewModel!!.searchFragmentViewState.value = EMPTY
            } else {
                adapter!!.replaceUsers(it)
                viewModel!!.searchFragmentViewState.value = ITEMS
            }
        })

        viewModel!!.searchFragmentViewState.observe(viewLifecycleOwner, Observer {
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (it) {
                DEFAULT -> showDefault()
                LOADING -> showLoading()
                EMPTY -> showEmpty()
                ITEMS -> showItems()
            }
        })

        return view
    }

    private fun showDefault() {
        friends_recycler_view_people.visibility = View.GONE
        friends_placeholder_people_empty.visibility = View.GONE
        friends_placeholder_people_loading.visibility = View.GONE
        friends_placeholder_people_default.visibility = View.VISIBLE
    }

    private fun showLoading() {
        friends_recycler_view_people.visibility = View.GONE
        friends_placeholder_people_empty.visibility = View.GONE
        friends_placeholder_people_default.visibility = View.GONE
        friends_placeholder_people_loading.visibility = View.VISIBLE
    }

    private fun showEmpty() {
        friends_recycler_view_people.visibility = View.GONE
        friends_placeholder_people_loading.visibility = View.GONE
        friends_placeholder_people_default.visibility = View.GONE
        friends_placeholder_people_empty.visibility = View.VISIBLE
    }

    private fun showItems() {
        friends_placeholder_people_empty.visibility = View.GONE
        friends_placeholder_people_loading.visibility = View.GONE
        friends_placeholder_people_default.visibility = View.GONE
        friends_recycler_view_people.visibility = View.VISIBLE
    }
}
