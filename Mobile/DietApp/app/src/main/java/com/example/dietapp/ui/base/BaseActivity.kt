package com.example.dietapp.ui.base

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.ui.nointernet.NoInternetActivity
import com.example.dietapp.utils.Constants
import com.example.dietapp.utils.removeToken
import com.example.dietapp.utils.removeUser

abstract class BaseActivity : AppCompatActivity() {

    private val sharedPreferences: SharedPreferences = application.getSharedPreferences(
        Constants.sharedPreferencesFileKey,
        Context.MODE_PRIVATE
    )

    fun logout() {
        sharedPreferences.removeUser()
        sharedPreferences.removeToken()

        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    fun showConnectionError() {
        startActivityForResult(
            Intent(this, NoInternetActivity::class.java),
            Constants.requestCodeNoInternet
        )
    }
}