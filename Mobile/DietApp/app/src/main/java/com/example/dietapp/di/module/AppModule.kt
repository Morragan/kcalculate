package com.example.dietapp.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.dietapp.R
import com.example.dietapp.utils.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
    @Provides
    @Singleton
    @JvmStatic
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences(
            Constants.sharedPreferencesFileKey,
            Context.MODE_PRIVATE
        )

}