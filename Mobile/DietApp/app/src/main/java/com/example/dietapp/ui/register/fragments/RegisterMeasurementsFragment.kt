package com.example.dietapp.ui.register.fragments


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.example.dietapp.R
import com.example.dietapp.ui.register.RegisterActivity
import com.example.dietapp.utils.Enums
import kotlinx.android.synthetic.main.fragment_register_measurements.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterMeasurementsFragment : RegisterFragment() {

    private var heightUnit = Enums.HeightUnit.Centimeters
    private var weightUnit = Enums.WeightUnit.Kilograms

    override fun passData() {
        val registerActivity = activity as RegisterActivity

        val heightCm: Float =
            if (heightUnit == Enums.HeightUnit.Centimeters)
                register_input_height_cm.text.toString().toFloat()
            else
                register_input_height_ft.text.toString().toFloat() * 30.48f + register_input_height_in.text.toString().toFloat() * 2.54f

        val weight = register_input_weight.text.toString().toFloat()
        val weightKg: Float =
            if (weightUnit == Enums.WeightUnit.Kilograms) weight else weight * 0.4536f

        val gender = if(register_radio_button_gender_male.isChecked) Enums.Gender.Male else Enums.Gender.Female

        registerActivity.heightCm = heightCm
        registerActivity.weightKg = weightKg
        registerActivity.gender = gender

    }

    override fun validate(): Boolean {
        var isValid = true

        val heightCm = register_input_height_cm.text.toString().toFloatOrNull()
        val heightFt = register_input_height_ft.text.toString().toIntOrNull()
        val heightIn = register_input_height_in.text.toString().toIntOrNull()

        if (heightUnit == Enums.HeightUnit.Centimeters && (heightCm == null || heightCm <= 0f)) {
            isValid = false
            register_input_height_cm.error = getString(R.string.error_height_input)
        }
        if (heightUnit == Enums.HeightUnit.Feet && (heightFt == null || heightFt <= 0)) {
            isValid = false
            register_input_height_ft.error = getString(R.string.error_height_input)
        }
        if (heightUnit == Enums.HeightUnit.Feet && (heightIn == null || heightIn <= 0 || heightIn >= 12)) {
            isValid = false
            register_input_height_in.error = getString(R.string.error_height_input)
        }

        val weight = register_input_weight.text.toString().toFloatOrNull()
        if (weight == null || weight <= 0) {
            isValid = false
            register_input_weight.error = getString(R.string.error_weight_input)
        }

        if(!register_radio_button_gender_male.isChecked && !register_radio_button_gender_female.isChecked){
            isValid = false
            register_radio_button_gender_male.error = getString(R.string.error_gender_button)
            register_radio_button_gender_female.error = getString(R.string.error_gender_button)
        }

        return isValid
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_measurements, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register_spinner_unit_height.onItemSelectedListener =
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
                        register_input_layout_height_ft.visibility = View.GONE
                        register_input_layout_height_in.visibility = View.GONE
                        register_input_layout_height_cm.visibility = View.VISIBLE
                    } else {
                        heightUnit = Enums.HeightUnit.Feet
                        register_input_layout_height_cm.visibility = View.GONE
                        register_input_layout_height_ft.visibility = View.VISIBLE
                        register_input_layout_height_in.visibility = View.VISIBLE
                    }

                }

            }
        register_spinner_unit_weight.onItemSelectedListener =
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
