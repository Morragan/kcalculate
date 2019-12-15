package com.example.dietapp.ui.register.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.example.dietapp.R
import com.example.dietapp.ui.register.RegisterActivity
import com.example.dietapp.ui.register.RegisterViewModel
import com.example.dietapp.utils.Enums

class RegisterQuizFragment : RegisterFragment() {

    companion object {
        @JvmStatic
        fun newInstance(_viewModel: RegisterViewModel) =
            RegisterQuizFragment().apply {
                viewModel = _viewModel
            }
    }

    override fun passData(activity: RegisterActivity) {
        val weightGoal = when {
            activity.findViewById<RadioButton>(R.id.register_radio_button_goal_lose_weight).isChecked -> Enums.WeightGoal.Lose
            activity.findViewById<RadioButton>(R.id.register_radio_button_goal_maintain_weight).isChecked -> Enums.WeightGoal.Maintain
            else -> Enums.WeightGoal.Gain
        }

        val activityLevel = when {
            activity.findViewById<RadioButton>(R.id.register_radio_button_activity_level_very_low).isChecked -> Enums.ActivityLevel.VeryLow
            activity.findViewById<RadioButton>(R.id.register_radio_button_activity_level_low).isChecked -> Enums.ActivityLevel.Low
            activity.findViewById<RadioButton>(R.id.register_radio_button_activity_level_moderate).isChecked -> Enums.ActivityLevel.Moderate
            activity.findViewById<RadioButton>(R.id.register_radio_button_activity_level_high).isChecked -> Enums.ActivityLevel.High
            else -> Enums.ActivityLevel.VeryHigh
        }

        activity.weightGoal = weightGoal
        activity.activityLevel = activityLevel
    }

    override fun validate(activity: RegisterActivity) = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_quiz, container, false)
    }


}
