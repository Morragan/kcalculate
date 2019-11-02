package com.example.dietapp.ui.register

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.dietapp.ui.register.fragments.*

class RegisterPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        private const val FRAGMENTS_COUNT = 5
    }

    override fun getItem(position: Int) = when (position) {
        0 -> RegisterMethodFragment()
        1 -> RegisterBasicsFragment()
        2 -> RegisterMeasurementsFragment()
        3 -> RegisterQuizFragment()
        else -> RegisterResultFragment()

//        4->RegisterFatPercentageFragment()
    }

    override fun getCount() = FRAGMENTS_COUNT
}