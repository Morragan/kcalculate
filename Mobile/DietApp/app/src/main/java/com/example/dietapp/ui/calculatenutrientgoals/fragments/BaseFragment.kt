package com.example.dietapp.ui.calculatenutrientgoals.fragments

import androidx.fragment.app.Fragment
import com.example.dietapp.ui.calculatenutrientgoals.CalculateNutrientGoalsViewModel

abstract class BaseFragment : Fragment() {
    var viewModel: CalculateNutrientGoalsViewModel? = null

    abstract fun validate(): Boolean
    abstract fun passData()
}