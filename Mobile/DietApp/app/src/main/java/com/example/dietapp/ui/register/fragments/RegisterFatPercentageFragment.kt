package com.example.dietapp.ui.register.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dietapp.R
import com.example.dietapp.ui.register.RegisterActivity
import com.example.dietapp.ui.register.RegisterViewModel
import com.example.dietapp.utils.Enums
import kotlinx.android.synthetic.main.fragment_register_fat_percentage.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterFatPercentageFragment : RegisterFragment() {

    companion object {
        @JvmStatic
        fun newInstance(_viewModel: RegisterViewModel) =
            RegisterFatPercentageFragment().apply {
                viewModel = _viewModel
            }
    }

    override fun passData(activity: RegisterActivity) {
        val fatPercentage = when {
            register_radio_button_fat_class1.isChecked -> Enums.BodyFatPercentage.Class1
            register_radio_button_fat_class2.isChecked -> Enums.BodyFatPercentage.Class2
            register_radio_button_fat_class3.isChecked -> Enums.BodyFatPercentage.Class3
            register_radio_button_fat_class4.isChecked -> Enums.BodyFatPercentage.Class4
            register_radio_button_fat_class5.isChecked -> Enums.BodyFatPercentage.Class5
            else -> Enums.BodyFatPercentage.Class6
        }
        //activity.fatPercentage = fatPercentage
    }

    override fun validate(activity: RegisterActivity) = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_fat_percentage, container, false)
    }
}
