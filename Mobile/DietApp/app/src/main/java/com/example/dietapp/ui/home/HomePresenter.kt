package com.example.dietapp.ui.home

import com.example.dietapp.api.MealEntriesService
import com.example.dietapp.di.scopes.ActivityScope
import com.example.dietapp.ui.base.BasePresenter
import javax.inject.Inject

@ActivityScope
class HomePresenter @Inject constructor(private val mealEntriesService: MealEntriesService): BasePresenter<HomeView>() {
    fun getMealEntries(){

    }

}