package com.example.dietapp.di.component

import com.example.dietapp.di.module.ApiModule
import com.example.dietapp.di.module.AppModule
import com.example.dietapp.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {
    fun inject(target: MainActivity)
}