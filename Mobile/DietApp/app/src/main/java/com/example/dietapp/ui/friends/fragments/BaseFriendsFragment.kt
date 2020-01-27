package com.example.dietapp.ui.friends.fragments

import androidx.fragment.app.Fragment
import com.example.dietapp.ui.friends.FriendsAdapter
import com.example.dietapp.ui.friends.FriendsViewModel

abstract class BaseFriendsFragment : Fragment() {
    var adapter: FriendsAdapter? = null
    var viewModel: FriendsViewModel? = null
}