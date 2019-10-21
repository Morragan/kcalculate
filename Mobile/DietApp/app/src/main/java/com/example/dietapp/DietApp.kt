package com.example.dietapp

import android.app.Application
import com.example.dietapp.di.component.AppComponent
import com.example.dietapp.di.component.DaggerAppComponent
import com.example.dietapp.models.MealDTO
import com.example.dietapp.models.MealEntryDTO
import com.example.dietapp.models.UserDTO

class DietApp : Application() {
    lateinit var appComponent: AppComponent

    // If possible, move state to Room, replace fields with observables
    companion object {

        var mealEntries: List<MealEntryDTO>? = null
        var meals: List<MealDTO>? = null
        var user: UserDTO? = null

    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}