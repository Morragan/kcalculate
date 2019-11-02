package com.example.dietapp.ui.friends

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.dietapp.R
import com.example.dietapp.ui.friends.fragments.FriendsFriendsFragment
import com.example.dietapp.ui.friends.fragments.FriendsPeopleFragment

class FriendsPagerAdapter(fm: FragmentManager, private val activity: FriendsActivity) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val NUM_PAGES = 2
    }

    private val fragments = listOf(
        FriendsFriendsFragment.newInstance(FriendsAdapter(activity, activity, activity)),
        FriendsPeopleFragment.newInstance(FriendsAdapter(activity, activity, activity))
    )

    override fun getPageTitle(position: Int): String = when (position) {
        0 -> activity.getString(R.string.tab_text_friends)
        else -> activity.getString(R.string.tab_text_people)
    }

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = NUM_PAGES
}