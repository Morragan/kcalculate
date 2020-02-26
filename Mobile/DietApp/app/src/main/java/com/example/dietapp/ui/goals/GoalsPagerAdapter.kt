package com.example.dietapp.ui.goals

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.dietapp.R
import com.example.dietapp.ui.goals.fragments.GoalFragment
import com.example.dietapp.ui.goals.fragments.RankingFragment

class GoalsPagerAdapter(fm: FragmentManager,
                        private val activity: GoalsActivity, viewModel: GoalsViewModel ): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val NUM_PAGES = 2
    }

    val fragments = listOf(GoalFragment.newInstance(viewModel), RankingFragment.newInstance(viewModel))

    override fun getItem(position: Int) = fragments[position]

    override fun getPageTitle(position: Int): String = when(position){
        0 -> activity.getString(R.string.tab_text_goal)
        else -> activity.getString(R.string.tab_text_ranking)
    }

    override fun getCount() = NUM_PAGES
}