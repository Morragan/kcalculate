package com.example.dietapp.ui.register.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.example.dietapp.R
import com.example.dietapp.ui.register.RegisterActivity
import com.example.dietapp.ui.register.RegisterViewModel
import com.example.dietapp.utils.Enums
import kotlinx.android.synthetic.main.fragment_register_measurements.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterMeasurementsFragment : RegisterFragment() {

    companion object {
        @JvmStatic
        fun newInstance(_viewModel: RegisterViewModel) =
            RegisterMeasurementsFragment().apply {
                viewModel = _viewModel
            }
    }

    private var heightUnit = Enums.HeightUnit.Centimeters
    private var weightUnit = Enums.WeightUnit.Kilograms

    override fun passData(activity: RegisterActivity) {
        val registerInputHeightCm = activity.findViewById<EditText>(R.id.register_input_height_cm)
        val registerInputHeightFt = activity.findViewById<EditText>(R.id.register_input_height_ft)
        val registerInputHeightIn = activity.findViewById<EditText>(R.id.register_input_height_in)

        val registerInputWeight = activity.findViewById<EditText>(R.id.register_input_weight)

        val registerRBGenderMale =
            activity.findViewById<RadioButton>(R.id.register_radio_button_gender_male)

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

        activity.heightCm = heightCm
        activity.weightKg = weightKg
        activity.gender = gender

    }

    override fun validate(activity: RegisterActivity): Boolean {
        var isValid = true

        val registerInputHeightCm = activity.findViewById<EditText>(R.id.register_input_height_cm)
        val registerInputHeightFt = activity.findViewById<EditText>(R.id.register_input_height_in)
        val registerInputHeightIn = activity.findViewById<EditText>(R.id.register_input_height_ft)

        val registerInputWeight = activity.findViewById<EditText>(R.id.register_input_weight)

        val registerRBGenderMale =
            activity.findViewById<RadioButton>(R.id.register_radio_button_gender_male)
        val registerRBGenderFemale =
            activity.findViewById<RadioButton>(R.id.register_radio_button_gender_female)

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
