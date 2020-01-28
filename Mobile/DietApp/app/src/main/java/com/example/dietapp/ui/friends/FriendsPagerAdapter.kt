package com.example.dietapp.ui.friends

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.dietapp.R
import com.example.dietapp.ui.friends.fragments.BlockedFragment
import com.example.dietapp.ui.friends.fragments.FindPeopleFragment
import com.example.dietapp.ui.friends.fragments.FriendsFragment
import com.example.dietapp.ui.friends.fragments.PendingRequestsFragment

class FriendsPagerAdapter(
    fm: FragmentManager,
    private val activity: FriendsActivity,
    viewModel: FriendsViewModel
) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val NUM_PAGES = 4
    }

    val fragments = listOf(
        FriendsFragment.newInstance(
            FriendsAdapter(activity, activity, activity, activity, activity),
            viewModel
        ),
        FindPeopleFragment.newInstance(
            FriendsAdapter(activity, activity, activity, activity, activity),
            viewModel
        ),
        PendingRequestsFragment.newInstance(
            FriendsAdapter(activity, activity, activity, activity, activity),
            viewModel
        ),
        BlockedFragment.newInstance(
            FriendsAdapter(activity, activity, activity, activity, activity),
            viewModel
        )
    )

    override fun getPageTitle(position: Int): String = when (position) {
        0 -> activity.getString(R.string.tab_text_friends)
        1 -> activity.getString(R.string.tab_text_people)
        2 -> activity.getString(R.string.tab_text_pending)
        else -> activity.getString(R.string.tab_text_blocked)
    }

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = NUM_PAGES
}