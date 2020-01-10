package com.example.dietapp.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.ui.home.HomeActivity
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.utils.getToken
import com.example.dietapp.ViewModelFactory
import kotlinx.coroutines.Job
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as DietApp).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()

        val token = sharedPreferences.getToken()
        if (token.isExpired()) logout()
        else login()
    }

    private fun login() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

}
