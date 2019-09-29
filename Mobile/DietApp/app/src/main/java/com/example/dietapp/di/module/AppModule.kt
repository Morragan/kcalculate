package com.example.dietapp.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val baseApp: Application){
    @Provides
    @Singleton
    fun provideApplication(): Application = baseApp

}