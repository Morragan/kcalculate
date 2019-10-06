package com.example.dietapp.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.dietapp.R
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
            application.getString(R.string.sharedprefs_file_key),
            Context.MODE_PRIVATE
        )

}