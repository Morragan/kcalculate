package com.example.dietapp.ui.goals.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.dietapp.R
import com.example.dietapp.ui.goals.GoalsViewModel
import com.example.dietapp.ui.goals.RankingAdapter
import com.example.dietapp.utils.ViewState
import kotlinx.android.synthetic.main.fragment_goal.*
import kotlinx.android.synthetic.main.fragment_ranking.*

/**
 * A simple [Fragment] subclass.
 */
class RankingFragment : BaseGoalFragment() {

    companion object {
        @JvmStatic
        fun newInstance(_viewModel: GoalsViewModel) =
            RankingFragment().apply {
                viewModel = _viewModel
            }
    }

    private val rankingAdapter by lazy { RankingAdapter(context!!) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ranking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        goals_ranking_recycler_view.adapter = rankingAdapter
        goals_ranking_recycler_view.layoutManager = LinearLayoutManager(context)

        viewModel!!.rankingTabViewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                ViewState.LOADING -> showLoading()
                else -> showRanking()
            }
        })

        viewModel!!.participants.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                rankingAdapter.replaceFriends(it)
                viewModel!!.rankingTabViewState.value = ViewState.ITEMS
            }
        })
    }

    private fun showLoading() {
        goals_ranking_recycler_view.visibility = View.GONE
        goals_ranking_loading_bar.visibility = View.VISIBLE
    }

    private fun showRanking() {
        goals_ranking_loading_bar.visibility = View.GONE
        goals_ranking_recycler_view.visibility = View.VISIBLE
    }
}
