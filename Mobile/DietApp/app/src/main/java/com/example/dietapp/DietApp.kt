package com.example.dietapp

import android.app.Application
import com.example.dietapp.di.component.AppComponent
import com.example.dietapp.di.component.DaggerAppComponent

class DietApp: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = initDaggerApp()
    }

    private fun initDaggerApp(): AppComponent =
        DaggerAppComponent.builder()
            .application(this)
            .build()

}