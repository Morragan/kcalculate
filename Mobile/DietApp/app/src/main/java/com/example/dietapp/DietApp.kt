package com.example.dietapp

import android.app.Application
import com.example.dietapp.di.component.AppComponent
import com.example.dietapp.di.component.DaggerAppComponent
import com.example.dietapp.models.MealDTO
import com.example.dietapp.models.MealEntryDTO
import com.example.dietapp.models.MergedFriend
import com.example.dietapp.models.UserDTO

class DietApp : Application() {
    lateinit var appComponent: AppComponent

    //TODO: If possible, move state to Room, replace fields with observables, DO NOT leave as it is
    companion object {

        var mealEntries: List<MealEntryDTO>? = null
        var meals: MutableList<MealDTO> = mutableListOf()
        var filteredMeals: MutableList<MealDTO> = mutableListOf()
        var user: UserDTO? = null
        var friends: MutableList<MergedFriend> = mutableListOf()
        var filteredFriends: MutableList<MergedFriend> = mutableListOf()

    }

    init {
        filteredMeals.addAll(meals)
        filteredFriends.addAll(friends)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}