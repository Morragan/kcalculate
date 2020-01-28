package com.example.dietapp.ui.register

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.dietapp.ui.register.fragments.RegisterBasicsFragment
import com.example.dietapp.ui.register.fragments.RegisterLoadingFragment
import com.example.dietapp.ui.register.fragments.RegisterMethodFragment

class RegisterPagerAdapter(
    fragmentManager: FragmentManager,
    viewModel: RegisterViewModel
) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        private const val FRAGMENTS_COUNT = 3
    }

    val fragments = listOf(
        RegisterMethodFragment.newInstance(viewModel),
        RegisterBasicsFragment.newInstance(viewModel),
        RegisterLoadingFragment.newInstance(viewModel)
    )

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = FRAGMENTS_COUNT
}