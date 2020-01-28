package com.example.dietapp.ui.calculatenutrientgoals

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.ViewModelFactory
import com.example.dietapp.utils.Constants
import kotlinx.android.synthetic.main.activity_calculate_nutrient_goals.*
import javax.inject.Inject

class CalculateNutrientGoalsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: CalculateNutrientGoalsViewModel
    private val viewPagerAdapter by lazy {
        CalculateNutrientGoalsPagerAdapter(
            supportFragmentManager,
            viewModel
        )
    }
    private val skipDialog by lazy {
        AlertDialog.Builder(this)
            .setMessage(R.string.calculate_goals_dialog_message)
            .setPositiveButton(R.string.calculate_goals_dialog_positive_button)
            { _, _ -> }
            .setNegativeButton(R.string.calculate_goals_dialog_negative_button)
            { _, _ ->
                calculate_goals_view_pager.currentItem = 2
            }
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_nutrient_goals)

        (application as DietApp).appComponent.inject(this)
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(CalculateNutrientGoalsViewModel::class.java)

        calculate_goals_view_pager.adapter = viewPagerAdapter
        skipDialog.show()

        calculate_goals_button_proceed.setOnClickListener {
            val position = calculate_goals_view_pager.currentItem
            val fragment = viewPagerAdapter.fragments[position]
            if (!fragment.validate()) return@setOnClickListener
            fragment.passData()

            if (calculate_goals_view_pager.currentItem == 2) {
                val nutrientGoals = Intent().apply {
                    putExtra(
                        Constants.intentKeyCalculateNutrientGoalsResult,
                        viewModel.nutrientGoals
                    )
                }
                setResult(Activity.RESULT_OK, nutrientGoals)
                finish()
            } else calculate_goals_view_pager.currentItem++
        }
    }

    override fun onBackPressed() {
        if (calculate_goals_view_pager.currentItem == 0) super.onBackPressed()
        else calculate_goals_view_pager.currentItem--
    }
}
