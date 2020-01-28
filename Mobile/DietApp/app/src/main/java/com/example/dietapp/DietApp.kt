package com.example.dietapp

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import com.example.dietapp.db.repositories.AccountRepository
import com.example.dietapp.di.component.AppComponent
import com.example.dietapp.di.component.DaggerAppComponent
import com.example.dietapp.models.dto.MealDTO
import com.example.dietapp.models.dto.MealEntryDTO
import com.example.dietapp.models.entity.Friend
import com.example.dietapp.models.dto.UserDTO
import com.example.dietapp.ui.login.LoginActivity

class DietApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}