package com.example.dietapp.ui.register.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dietapp.R
import com.example.dietapp.ui.register.RegisterActivity
import com.example.dietapp.utils.Enums
import kotlinx.android.synthetic.main.fragment_register_quiz.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterQuizFragment : RegisterFragment() {
    override fun passData() {
        val registerActivity = activity as RegisterActivity

        val weightGoal = when {
            register_radio_button_goal_lose_weight.isChecked -> Enums.WeightGoal.Lose
            register_radio_button_goal_maintain_weight.isChecked -> Enums.WeightGoal.Maintain
            else -> Enums.WeightGoal.Gain
        }

        val activityLevel = when {
            register_radio_button_activity_level_very_low.isChecked -> Enums.ActivityLevel.VeryLow
            register_radio_button_activity_level_low.isChecked -> Enums.ActivityLevel.Low
            register_radio_button_activity_level_moderate.isChecked -> Enums.ActivityLevel.Moderate
            register_radio_button_activity_level_high.isChecked -> Enums.ActivityLevel.High
            else -> Enums.ActivityLevel.VeryHigh
        }

        registerActivity.weightGoal = weightGoal
        registerActivity.activityLevel = activityLevel
    }

    override fun validate() = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_quiz, container, false)
    }


}
