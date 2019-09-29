package com.example.dietapp.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dietapp.R

class LoginActivity : AppCompatActivity(), LoginView {
    override fun showConnectionFailure() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
