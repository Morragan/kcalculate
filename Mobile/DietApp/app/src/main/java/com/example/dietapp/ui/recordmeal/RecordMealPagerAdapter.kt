package com.example.dietapp.ui.recordmeal

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.dietapp.R
import com.example.dietapp.ui.recordmeal.fragments.RecordMealAllMealsFragment
import com.example.dietapp.ui.recordmeal.fragments.RecordMealUserMealsFragment

class RecordMealPagerAdapter(
    fm: FragmentManager,
    private val activity: RecordMealActivity,
    viewModel: RecordMealViewModel
) :
    FragmentPagerAdapter(
        fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    companion object {
        private const val NUM_PAGES = 2
    }

    private val fragments = listOf(
        RecordMealUserMealsFragment.newInstance(
            RecordMealAdapter(activity, activity), viewModel
        ), RecordMealAllMealsFragment.newInstance(
            RecordMealAdapter(activity, activity), viewModel
        )
    )

    override fun getPageTitle(position: Int): String = when (position) {
        0 -> activity.getString(R.string.tab_text_my_meals)
        else -> activity.getString(R.string.tab_text_all_meals)
    }

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = NUM_PAGES
}