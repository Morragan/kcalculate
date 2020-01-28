package com.example.dietapp.di.component

import android.app.Application
import com.example.dietapp.di.module.ApiModule
import com.example.dietapp.di.module.AppModule
import com.example.dietapp.di.module.DatabaseModule
import com.example.dietapp.di.module.ViewModelModule
import com.example.dietapp.ui.calculatenutrientgoals.CalculateNutrientGoalsActivity
import com.example.dietapp.ui.createmeal.CreateMealActivity
import com.example.dietapp.ui.friends.FriendsActivity
import com.example.dietapp.ui.home.HomeActivity
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.ui.main.MainActivity
import com.example.dietapp.ui.profile.ProfileActivity
import com.example.dietapp.ui.recordmeal.RecordMealActivity
import com.example.dietapp.ui.register.RegisterActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, DatabaseModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(target: MainActivity)
    fun inject(target: LoginActivity)
    fun inject(target: RegisterActivity)
    fun inject(target: HomeActivity)
    fun inject(target: RecordMealActivity)
    fun inject(target: CreateMealActivity)
    fun inject(target: FriendsActivity)
    fun inject(target: ProfileActivity)
    fun inject(target: CalculateNutrientGoalsActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

}