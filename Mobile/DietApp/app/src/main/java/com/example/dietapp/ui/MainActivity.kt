package com.example.dietapp.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.api.AccountService
import com.example.dietapp.ui.home.HomeActivity
import com.example.dietapp.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as DietApp).appComponent.inject(this)

        val tokenExpiration = sharedPreferences.getLong(getString(R.string.preference_token_expiration), 0)
        if(System.currentTimeMillis()/1000L > tokenExpiration) {
            // Token expired
            //TODO: instead, try to refresh token, move to onResume
            with(sharedPreferences.edit()) {
                remove(getString(R.string.preference_token_expiration))
                remove(getString(R.string.preference_access_token))
                remove(getString(R.string.preference_refresh_token))
                apply()
            }
            startActivity(Intent(this, LoginActivity::class.java))
        }
        else{
            // Token is valid (unless server restarted)
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
