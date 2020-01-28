package com.example.dietapp.ui.calculatenutrientgoals.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.example.dietapp.R
import com.example.dietapp.ui.calculatenutrientgoals.CalculateNutrientGoalsViewModel
import com.example.dietapp.utils.Enums
import kotlinx.android.synthetic.main.fragment_measurements.*

/**
 * A simple [Fragment] subclass.
 */
class MeasurementsFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance(_viewModel: CalculateNutrientGoalsViewModel) =
            MeasurementsFragment().apply {
                viewModel = _viewModel
            }
    }

    private var heightUnit = Enums.HeightUnit.Centimeters
    private var weightUnit = Enums.WeightUnit.Kilograms

    override fun validate(): Boolean {
        var isValid = true

        val registerInputHeightCm = calculate_goals_input_height_cm
        val registerInputHeightFt = calculate_goals_input_height_in
        val registerInputHeightIn = calculate_goals_input_height_ft

        val registerInputWeight = calculate_goals_input_weight

        val registerRBGenderMale = calculate_goals_radio_button_gender_male
        val registerRBGenderFemale = calculate_goals_radio_button_gender_female

        val heightCm = registerInputHeightCm.text.toString().toFloatOrNull()
        val heightFt = registerInputHeightFt.text.toString().toIntOrNull()
        val heightIn = registerInputHeightIn.text.toString().toIntOrNull()

        if (heightUnit == Enums.HeightUnit.Centimeters && (heightCm == null || heightCm <= 0f)) {
            isValid = false
            registerInputHeightCm.error = getString(R.string.error_height_input)
        }
        if (heightUnit == Enums.HeightUnit.Feet && (heightFt == null || heightFt <= 0)) {
            isValid = false
            registerInputHeightFt.error = getString(R.string.error_height_input)
        }
        if (heightUnit == Enums.HeightUnit.Feet && (heightIn == null || heightIn <= 0 || heightIn >= 12)) {
            isValid = false
            registerInputHeightIn.error = getString(R.string.error_height_input)
        }

        val weight = registerInputWeight.text.toString().toFloatOrNull()
        if (weight == null || weight <= 0) {
            isValid = false
            registerInputWeight.error = getString(R.string.error_weight_input)
        }

        if (!registerRBGenderMale.isChecked && !registerRBGenderFemale.isChecked) {
            isValid = false
            registerRBGenderMale.error = getString(R.string.error_gender_button)
            registerRBGenderFemale.error = getString(R.string.error_gender_button)
        }

        return isValid
    }

    override fun passData() {
        val registerInputHeightCm = calculate_goals_input_height_cm
        val registerInputHeightFt = calculate_goals_input_height_ft
        val registerInputHeightIn = calculate_goals_input_height_in

        val registerInputWeight = calculate_goals_input_weight

        val registerRBGenderMale = calculate_goals_radio_button_gender_male

        val heightCm: Float =
            if (heightUnit == Enums.HeightUnit.Centimeters)
                registerInputHeightCm.text.toString().toFloat()
            else
                registerInputHeightFt.text.toString().toFloat() * 30.48f + registerInputHeightIn.text.toString().toFloat() * 2.54f

        val weight = registerInputWeight.text.toString().toFloat()
        val weightKg: Float =
            if (weightUnit == Enums.WeightUnit.Kilograms) weight else weight * 0.4536f

        val gender =
            if (registerRBGenderMale.isChecked) Enums.Gender.Male else Enums.Gender.Female

        viewModel!!.setMeasurements(heightCm, weightKg, gender)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_measurements, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calculate_goals_spinner_unit_height.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        heightUnit = Enums.HeightUnit.Centimeters
                        calculate_goals_input_layout_height_ft.visibility = View.GONE
                        calculate_goals_input_layout_height_in.visibility = View.GONE
                        calculate_goals_input_layout_height_cm.visibility = View.VISIBLE
                    } else {
                        heightUnit = Enums.HeightUnit.Feet
                        calculate_goals_input_layout_height_cm.visibility = View.GONE
                        calculate_goals_input_layout_height_ft.visibility = View.VISIBLE
                        calculate_goals_input_layout_height_in.visibility = View.VISIBLE
                    }
                }
            }

        calculate_goals_spinner_unit_weight.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    weightUnit =
                        if (position == 0) Enums.WeightUnit.Kilograms else Enums.WeightUnit.Pounds
                }
            }
    }
}
