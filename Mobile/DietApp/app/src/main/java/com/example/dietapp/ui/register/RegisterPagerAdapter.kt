package com.example.dietapp.ui.register

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.dietapp.ui.register.fragments.*

class RegisterPagerAdapter(
    fragmentManager: FragmentManager,
    private val viewModel: RegisterViewModel
) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        private const val FRAGMENTS_COUNT = 5
    }

    override fun getItem(position: Int) = when (position) {
        0 -> RegisterMethodFragment.newInstance(viewModel)
        1 -> RegisterBasicsFragment.newInstance(viewModel)
        2 -> RegisterMeasurementsFragment.newInstance(viewModel)
        3 -> RegisterQuizFragment.newInstance(viewModel)
        else -> RegisterResultFragment.newInstance(viewModel)


//        4->RegisterFatPercentageFragment()
    }

    override fun getCount() = FRAGMENTS_COUNT
}