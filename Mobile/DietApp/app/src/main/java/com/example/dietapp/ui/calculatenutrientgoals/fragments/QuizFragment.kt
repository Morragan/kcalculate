package com.example.dietapp.ui.calculatenutrientgoals.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton

import com.example.dietapp.R
import com.example.dietapp.ui.calculatenutrientgoals.CalculateNutrientGoalsViewModel
import com.example.dietapp.utils.Enums
import kotlinx.android.synthetic.main.fragment_quiz.*

/**
 * A simple [Fragment] subclass.
 */
class QuizFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance(_viewModel: CalculateNutrientGoalsViewModel) =
            QuizFragment().apply {
                viewModel = _viewModel
            }
    }

    override fun validate() = true

    override fun passData() {
        val weightGoal = when {
            calculate_goals_radio_button_goal_lose_weight.isChecked -> Enums.WeightGoal.Lose
            calculate_goals_radio_button_goal_maintain_weight.isChecked -> Enums.WeightGoal.Maintain
            else -> Enums.WeightGoal.Gain
        }

        val activityLevel = when {
            calculate_goals_radio_button_activity_level_very_low.isChecked -> Enums.ActivityLevel.VeryLow
            calculate_goals_radio_button_activity_level_low.isChecked -> Enums.ActivityLevel.Low
            calculate_goals_radio_button_activity_level_moderate.isChecked -> Enums.ActivityLevel.Moderate
            calculate_goals_radio_button_activity_level_high.isChecked -> Enums.ActivityLevel.High
            else -> Enums.ActivityLevel.VeryHigh
        }

        viewModel!!.setQuizData(weightGoal, activityLevel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }
}
