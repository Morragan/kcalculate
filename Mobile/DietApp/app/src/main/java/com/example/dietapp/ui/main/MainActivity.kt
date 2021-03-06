package com.example.dietapp.ui.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.ui.home.HomeActivity
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.ui.nointernet.NoInternetActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenter

    override fun showConnectionError() {
        startActivity(Intent(this, NoInternetActivity::class.java))
    }

    override fun login() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as DietApp).appComponent.newActivityComponent().inject(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)
        presenter.checkUserLoggedIn()
    }

    override fun onStop() {
        presenter.unbind()
        super.onStop()
    }
}
