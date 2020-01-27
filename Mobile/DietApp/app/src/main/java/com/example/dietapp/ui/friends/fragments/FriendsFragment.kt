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
import kotlinx.android.synthetic.main.fragment_friends_friends.*

class FriendsFragment : BaseFriendsFragment() {

    companion object {
        @JvmStatic
        fun newInstance(_adapter: FriendsAdapter, _viewModel: FriendsViewModel) =
            FriendsFragment().apply {
                adapter = _adapter
                viewModel = _viewModel
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_friends_friends, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.friends_recycler_view_friends)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        viewModel!!.friends.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                viewModel!!.friendsFragmentViewState.value = EMPTY
            } else {
                adapter!!.replaceUsers(it)
                viewModel!!.friendsFragmentViewState.value = ITEMS
            }
        })

        viewModel!!.friendsFragmentViewState.observe(viewLifecycleOwner, Observer {
            @Suppress("NON_EXHAUSTIVE_WHEN")
            when (it) {
                EMPTY -> showEmpty()
                LOADING -> showLoading()
                ITEMS -> showItems()
            }
        })

        return view
    }

    private fun showLoading() {
        friends_recycler_view_friends.visibility = View.GONE
        friends_placeholder_friends_empty.visibility = View.GONE
        friends_placeholder_friends_loading.visibility = View.VISIBLE
    }

    private fun showEmpty() {
        friends_recycler_view_friends.visibility = View.GONE
        friends_placeholder_friends_loading.visibility = View.GONE
        friends_placeholder_friends_empty.visibility = View.VISIBLE
    }

    private fun showItems() {
        friends_placeholder_friends_empty.visibility = View.GONE
        friends_placeholder_friends_loading.visibility = View.GONE
        friends_recycler_view_friends.visibility = View.VISIBLE
    }
}
