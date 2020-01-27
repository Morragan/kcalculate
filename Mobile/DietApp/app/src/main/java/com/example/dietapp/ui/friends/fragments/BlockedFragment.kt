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
import kotlinx.android.synthetic.main.fragment_friends_blocked.*

/**
 * A simple [Fragment] subclass.
 */
class BlockedFragment : BaseFriendsFragment() {

    companion object {
        @JvmStatic
        fun newInstance(_adapter: FriendsAdapter, _viewModel: FriendsViewModel) =
            BlockedFragment().apply {
                adapter = _adapter
                viewModel = _viewModel
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_friends_blocked, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.friends_recycler_view_blocked)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        viewModel!!.blockedUsers.observe(viewLifecycleOwner, Observer{
            if(it.isEmpty()) {
                viewModel!!.blockedFragmentViewState.value = EMPTY
            } else{
                adapter!!.replaceUsers(it)
                viewModel!!.blockedFragmentViewState.value = ITEMS
            }
        })

        viewModel!!.blockedFragmentViewState.observe(viewLifecycleOwner, Observer{
            @Suppress("NON_EXHAUSTIVE_WHEN")
            when(it){
                EMPTY -> showEmpty()
                LOADING -> showLoading()
                ITEMS -> showItems()
            }
        })

        return view
    }

    private fun showLoading() {
        friends_recycler_view_blocked.visibility = View.GONE
        friends_placeholder_blocked_empty.visibility = View.GONE
        friends_placeholder_blocked_loading.visibility = View.VISIBLE
    }

    private fun showEmpty() {
        friends_recycler_view_blocked.visibility = View.GONE
        friends_placeholder_blocked_loading.visibility = View.GONE
        friends_placeholder_blocked_empty.visibility = View.VISIBLE
    }

    private fun showItems() {
        friends_placeholder_blocked_empty.visibility = View.GONE
        friends_placeholder_blocked_loading.visibility = View.GONE
        friends_recycler_view_blocked.visibility = View.VISIBLE
    }
}
