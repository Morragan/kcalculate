package com.example.dietapp.ui.calculatenutrientgoals

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.dietapp.ui.calculatenutrientgoals.fragments.MeasurementsFragment
import com.example.dietapp.ui.calculatenutrientgoals.fragments.QuizFragment
import com.example.dietapp.ui.calculatenutrientgoals.fragments.ResultFragment

class CalculateNutrientGoalsPagerAdapter(
    fragmentManager: FragmentManager,
    viewModel: CalculateNutrientGoalsViewModel
) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        private const val FRAGMENTS_COUNT = 3
    }

    val fragments = listOf(
        MeasurementsFragment.newInstance(viewModel),
        QuizFragment.newInstance(viewModel),
        ResultFragment.newInstance(viewModel)
    )

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = FRAGMENTS_COUNT
}