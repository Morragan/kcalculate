package com.example.dietapp

import android.app.Application
import com.example.dietapp.di.component.AppComponent
import com.example.dietapp.di.component.DaggerAppComponent
import com.example.dietapp.models.MealDTO
import com.example.dietapp.models.MealEntryDTO
import com.example.dietapp.models.UserDTO

class DietApp : Application() {
    lateinit var appComponent: AppComponent

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

//TODO: Dodać do credits <a href="https://www.freepik.com/free-photos-vectors/business">Business vector created by freepik - www.freepik.com</a>
// https://icons8.com