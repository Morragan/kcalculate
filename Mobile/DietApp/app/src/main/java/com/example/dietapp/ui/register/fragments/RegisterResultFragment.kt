package com.example.dietapp.ui.register.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.dietapp.R
import com.example.dietapp.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_register_result.*

class RegisterResultFragment : RegisterFragment() {
    override fun validate() = true

    override fun passData() {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        register_input_calorie_limit.setText((activity!! as RegisterActivity).calorieLimit.toString())
    }

    override fun onResume() {
        val proceedButton = activity!!.findViewById<Button>(R.id.register_button_proceed)
        val registerButton = activity!!.findViewById<Button>(R.id.register_button_register)

        proceedButton.visibility = View.GONE
        registerButton.visibility = View.VISIBLE

        super.onResume()
    }

    override fun onPause() {
        super.onPause()

        val proceedButton = activity!!.findViewById<Button>(R.id.register_button_proceed)
        val registerButton = activity!!.findViewById<Button>(R.id.register_button_register)

        proceedButton.visibility = View.VISIBLE
        registerButton.visibility = View.GONE

    }
}
