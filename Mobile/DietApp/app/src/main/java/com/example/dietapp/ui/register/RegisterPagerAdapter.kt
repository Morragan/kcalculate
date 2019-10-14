package com.example.dietapp.ui.register

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.dietapp.ui.register.fragments.*

class RegisterPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        private const val FRAGMENTS_COUNT = 6
    }

    private val fragments: List<RegisterFragment> = listOf(
        RegisterMethodFragment(),
        RegisterBasicsFragment(),
        RegisterMeasurementsFragment(),
        RegisterQuizFragment(),
        RegisterFatPercentageFragment(),
        RegisterResultFragment()
    )

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = FRAGMENTS_COUNT
}