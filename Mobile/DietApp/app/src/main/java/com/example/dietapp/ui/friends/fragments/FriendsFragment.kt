package com.example.dietapp.ui.friends.fragments

import androidx.fragment.app.Fragment
import com.example.dietapp.ui.friends.FriendsAdapter

abstract class FriendsFragment : Fragment() {
    var adapter: FriendsAdapter? = null
}