package com.example.dietapp.di.component

import com.example.dietapp.di.scopes.ActivityScope
import com.example.dietapp.ui.home.HomeActivity
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.ui.register.RegisterActivity
import dagger.Subcomponent

@Subcomponent
@ActivityScope
interface ActivityComponent {
    fun inject(target: LoginActivity)
    fun inject(target: RegisterActivity)
    fun inject(target: HomeActivity)
}