package com.example.dietapp.di.component

import android.app.Application
import android.content.SharedPreferences
import com.example.dietapp.api.AccountService
import com.example.dietapp.di.module.ApiModule
import com.example.dietapp.di.module.AppModule
import com.example.dietapp.di.module.DatabaseModule
import com.example.dietapp.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, DatabaseModule::class])
interface AppComponent {
    fun newActivityComponent(): ActivityComponent

    fun getAccountService(): AccountService
    fun getSharedPreferences(): SharedPreferences
    fun getApplication(): Application

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

}