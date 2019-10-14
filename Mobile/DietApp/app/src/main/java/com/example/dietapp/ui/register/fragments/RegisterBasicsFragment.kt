package com.example.dietapp.ui.register.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dietapp.R
import com.example.dietapp.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.fragment_register_basics.*

class RegisterBasicsFragment : RegisterFragment() {
    override fun passData() {
        val registerActivity = activity as RegisterActivity
        registerActivity.email = register_input_email.text.toString()
        registerActivity.nickname = register_input_nickname.text.toString()
        registerActivity.password = register_input_password.text.toString()
        registerActivity.avatarLink = register_input_avatar_link.text.toString()
    }

    override fun validate(): Boolean {
        var isValid = true

        val email = register_input_email.text.toString()
        val nickname = register_input_nickname.text.toString()
        val password = register_input_password.text.toString()

        if (email.isBlank()) {
            isValid = false
            register_input_email.error = getString(R.string.error_email_input)
        }
        if (nickname.isBlank()) {
            isValid = false
            register_input_nickname.error = getString(R.string.error_nickname_input)
        }
        if (password.isBlank() || password.length < 8) {
            isValid = false
            register_input_password.error = getString(R.string.error_password_input)
        }

        return isValid
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_basics, container, false)
    }
}