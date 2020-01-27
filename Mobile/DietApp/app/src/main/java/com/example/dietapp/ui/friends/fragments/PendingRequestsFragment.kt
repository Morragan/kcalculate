package com.example.dietapp.ui.friends.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.ui.friends.FriendsAdapter
import com.example.dietapp.ui.friends.FriendsViewModel
import com.example.dietapp.utils.ViewState.*
import kotlinx.android.synthetic.main.fragment_friends_pending.*

/**
 * A simple [Fragment] subclass.
 */
class PendingRequestsFragment : BaseFriendsFragment() {

    companion object {
        @JvmStatic
        fun newInstance(_adapter: FriendsAdapter, _viewModel: FriendsViewModel) =
            PendingRequestsFragment().apply {
                adapter = _adapter
                viewModel = _viewModel
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_friends_pending, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.friends_recycler_view_pending)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        viewModel!!.pendingFriends.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                viewModel!!.pendingFragmentViewState.value = EMPTY
            } else {
                adapter!!.replaceUsers(it)
                viewModel!!.pendingFragmentViewState.value = ITEMS
            }
        })

        viewModel!!.pendingFragmentViewState.observe(viewLifecycleOwner, Observer {
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
        friends_recycler_view_pending.visibility = View.GONE
        friends_placeholder_pending_empty.visibility = View.GONE
        friends_placeholder_pending_loading.visibility = View.VISIBLE
    }

    private fun showEmpty() {
        friends_recycler_view_pending.visibility = View.GONE
        friends_placeholder_pending_loading.visibility = View.GONE
        friends_placeholder_pending_empty.visibility = View.VISIBLE
    }

    private fun showItems() {
        friends_placeholder_pending_empty.visibility = View.GONE
        friends_placeholder_pending_loading.visibility = View.GONE
        friends_recycler_view_pending.visibility = View.VISIBLE
    }
}
