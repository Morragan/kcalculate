package com.example.dietapp

import android.app.Application
import com.example.dietapp.di.component.AppComponent
import com.example.dietapp.di.component.DaggerAppComponent
import com.example.dietapp.di.module.ApiModule
import com.example.dietapp.di.module.AppModule

class DietApp: Application() {
    lateinit var appComponent: AppComponent

    private fun initDagger(app: DietApp): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .apiModule(ApiModule())
            .build()

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }
}