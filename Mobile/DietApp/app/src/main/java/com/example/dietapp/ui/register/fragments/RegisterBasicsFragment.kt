package com.example.dietapp.ui.register.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.dietapp.R
import com.example.dietapp.ui.register.RegisterActivity
import com.example.dietapp.ui.register.RegisterViewModel

class RegisterBasicsFragment : RegisterFragment() {

    companion object {
        @JvmStatic
        fun newInstance(_viewModel: RegisterViewModel) =
            RegisterBasicsFragment().apply {
                viewModel = _viewModel
            }
    }

    override fun passData(activity: RegisterActivity) {
        val registerInputEmail = activity.findViewById<EditText>(R.id.register_input_email)
        val registerInputNickname = activity.findViewById<EditText>(R.id.register_input_nickname)
        val registerInputPassword = activity.findViewById<EditText>(R.id.register_input_password)
        val registerInputAvatarLink =
            activity.findViewById<EditText>(R.id.register_input_avatar_link)

        viewModel.email = registerInputEmail.text.toString()
        viewModel.nickname = registerInputNickname.text.toString()
        viewModel.password = registerInputPassword.text.toString()
        viewModel.avatarLink = registerInputAvatarLink.text.toString()
    }

    override fun validate(activity: RegisterActivity): Boolean {
        var isValid = true

        val registerInputEmail = activity.findViewById<EditText>(R.id.register_input_email)
        val registerInputNickname = activity.findViewById<EditText>(R.id.register_input_nickname)
        val registerInputPassword = activity.findViewById<EditText>(R.id.register_input_password)

        val email = registerInputEmail.text.toString()
        val nickname = registerInputNickname.text.toString()
        val password = registerInputPassword.text.toString()

        if (email.isBlank()) {
            isValid = false
            registerInputEmail.error = activity.getString(R.string.error_email_input)
        }
        if (nickname.isBlank()) {
            isValid = false
            registerInputNickname.error = activity.getString(R.string.error_nickname_input)
        }
        if (password.isBlank() || password.length < 8) {
            isValid = false
            registerInputPassword.error = activity.getString(R.string.error_password_input)
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
    // TODO: Data binding
    data class Data(
        var email: String,
        var nickname: String,
        var password: String,
        var avatarImageLink: String? = null
    )
}