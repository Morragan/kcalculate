package com.example.dietapp.di.component

import android.app.Application
import android.content.SharedPreferences
import com.example.dietapp.api.AccountService
import com.example.dietapp.di.module.ApiModule
import com.example.dietapp.di.module.AppModule
import com.example.dietapp.ui.MainActivity
import com.example.dietapp.ui.login.LoginActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {
    fun inject(target: MainActivity)
    fun inject(target: LoginActivity)
    fun getAccountService(): AccountService
    fun getSharedPreferences(): SharedPreferences
    fun getApplication(): Application

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}