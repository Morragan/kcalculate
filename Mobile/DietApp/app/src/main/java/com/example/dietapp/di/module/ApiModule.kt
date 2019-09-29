package com.example.dietapp.di.module

import com.example.dietapp.api.AccountService
import com.example.dietapp.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.apiBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory((RxJava2CallAdapterFactory.create()))
            .build()
    }

    @Provides
    fun provideAccountService(retrofit: Retrofit): AccountService{
        return retrofit.create(AccountService::class.java)
    }
}