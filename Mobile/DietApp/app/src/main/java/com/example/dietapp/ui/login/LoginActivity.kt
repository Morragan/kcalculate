package com.example.dietapp.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.models.dto.LoginDTO
import com.example.dietapp.ui.home.HomeActivity
import com.example.dietapp.ui.register.RegisterActivity
import com.example.dietapp.utils.Constants
import com.example.dietapp.utils.Converters
import com.example.dietapp.utils.removeToken
import com.example.dietapp.ViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    lateinit var viewModel: LoginViewModel

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        login_button_login.doneLoadingAnimation(
            ContextCompat.getColor(this, R.color.error),
            Converters.drawableToBitmap(getDrawable(R.drawable.ic_error_white)!!)
        )
        Handler().postDelayed({
            login_button_login.revertAnimation()
        }, 1000)
    }

    private fun startHomeActivity() {
        login_button_login.doneLoadingAnimation(
            ContextCompat.getColor(this, R.color.success),
            Converters.drawableToBitmap(getDrawable(R.drawable.ic_done_white)!!)
        )

        Handler().postDelayed({
            login_button_login.revertAnimation()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, 1000)
    }

    private fun showConnectionFailure() {
        login_text_connection_failure.visibility = View.VISIBLE

        login_button_login.doneLoadingAnimation(
            ContextCompat.getColor(this, R.color.error),
            Converters.drawableToBitmap(getDrawable(R.drawable.ic_error_white)!!)
        )

        Handler().postDelayed({
            login_button_login.revertAnimation()
        }, 1000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        (application as DietApp).appComponent.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        sharedPreferences.removeToken()

        val savedNickname = intent.getStringExtra(Constants.intentKeyRegisterToLoginNickname)
        if (savedNickname != null) {
            login_input_nickname.setText(savedNickname)
        }

        login_button_login.setOnClickListener {
            if (!validateForm()) return@setOnClickListener

            val nickname = login_input_nickname.text.toString()
            val password = login_input_password.text.toString()
            viewModel.login(LoginDTO(nickname, password))

            login_button_login.startAnimation()
        }

        login_link_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // region LiveData observers setup
        viewModel.isLoggedIn.observe(this, Observer { loggedIn ->
            if(loggedIn) startHomeActivity()
        })
        viewModel.hasInternetConnection.observe(this, Observer { hasInternetConnection ->
            if(!hasInternetConnection) showConnectionFailure()
        })
        // endregion
    }

    private fun validateForm(): Boolean {
        var valid = true

        if (login_input_nickname.text.isNullOrBlank()) {
            login_input_nickname.error = getString(R.string.error_nickname_input)
            valid = false
        } else
            login_input_nickname.error = null

        if (login_input_password.text.isNullOrBlank()) {
            login_input_password.error = getString(R.string.error_password_input)
            valid = false
        } else
            login_input_password.error = null

        return valid
    }
}
