package com.example.dietapp.ui.goals.fragments

import androidx.fragment.app.Fragment
import com.example.dietapp.ui.goals.GoalsViewModel

abstract class BaseGoalFragment: Fragment(){
    var viewModel: GoalsViewModel? = null
}