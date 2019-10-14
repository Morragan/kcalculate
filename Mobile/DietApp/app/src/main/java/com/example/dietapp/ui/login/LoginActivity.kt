package com.example.dietapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.ui.home.HomeActivity
import com.example.dietapp.ui.register.RegisterActivity
import com.example.dietapp.utils.Constants
import com.example.dietapp.utils.Converters
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginView {
    @Inject
    lateinit var presenter: LoginPresenter

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        login_button_login.doneLoadingAnimation(
            ContextCompat.getColor(this, R.color.error),
            Converters.drawableToBitmap(getDrawable(R.drawable.baseline_error_outline_black_48)!!)
        )
        Handler().postDelayed({
            login_button_login.revertAnimation()
        }, 1000)
    }

    override fun startHomeActivity() {
        login_button_login.doneLoadingAnimation(
            ContextCompat.getColor(this, R.color.success),
            Converters.drawableToBitmap(getDrawable(R.drawable.baseline_done_black_48)!!)
        )

        Handler().postDelayed({
            login_button_login.revertAnimation()
        }, 1000)

        startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun showConnectionFailure() {
        login_text_connection_failure.visibility = View.VISIBLE

        login_button_login.doneLoadingAnimation(
            ContextCompat.getColor(this, R.color.error),
            Converters.drawableToBitmap(getDrawable(R.drawable.baseline_error_outline_black_48)!!)
        )

        Handler().postDelayed({
            login_button_login.revertAnimation()
        }, 1000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val activityComponent = (application as DietApp).appComponent.newActivityComponent()
        activityComponent.inject(this)

        val nickname = intent.getStringExtra(Constants.intentKeyRegisterToLoginNickname)
        if (nickname != null) {
            login_input_nickname.setText(nickname)
        }

        login_button_login.setOnClickListener {
            if (!validateForm()) return@setOnClickListener

            val nickname = login_input_nickname.text.toString()
            val password = login_input_password.text.toString()
            presenter.onLoginButtonClick(nickname, password)

            login_button_login.startAnimation()
        }

        login_link_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)
    }

    override fun onStop() {
        presenter.unbind()
        super.onStop()
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
