package com.example.dietapp.ui.register

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.dietapp.ui.register.fragments.RegisterBasicsFragment
import com.example.dietapp.ui.register.fragments.RegisterMeasurementsFragment
import com.example.dietapp.ui.register.fragments.RegisterMethodFragment
import com.example.dietapp.ui.register.fragments.RegisterQuizFragment

class RegisterPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        private const val FRAGMENTS_COUNT = 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> RegisterMethodFragment()
            1 -> RegisterBasicsFragment()
            2 -> RegisterMeasurementsFragment()
            3 -> RegisterQuizFragment()
            else -> RegisterMethodFragment()
        }
    }


    override fun getCount(): Int {
        return FRAGMENTS_COUNT
    }
}