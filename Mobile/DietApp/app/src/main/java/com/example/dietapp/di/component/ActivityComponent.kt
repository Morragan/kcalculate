package com.example.dietapp.di.component

import com.example.dietapp.api.AccountService
import com.example.dietapp.di.scopes.ActivityScope
import com.example.dietapp.ui.createmeal.CreateMealActivity
import com.example.dietapp.ui.friends.FriendsActivity
import com.example.dietapp.ui.home.HomeActivity
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.ui.main.MainActivity
import com.example.dietapp.ui.recordmeal.RecordMealActivity
import com.example.dietapp.ui.register.RegisterActivity
import dagger.Subcomponent

@Subcomponent
@ActivityScope
interface ActivityComponent {
    fun getAccountService(): AccountService

    fun inject(target: MainActivity)
    fun inject(target: LoginActivity)
    fun inject(target: RegisterActivity)
    fun inject(target: HomeActivity)
    fun inject(target: RecordMealActivity)
    fun inject(target: CreateMealActivity)
    fun inject(target: FriendsActivity)
}