package com.example.dietapp.di.module

import androidx.lifecycle.ViewModel
import com.example.dietapp.di.injectionkeys.ViewModelKey
import com.example.dietapp.ui.createmeal.CreateMealViewModel
import com.example.dietapp.ui.home.HomeViewModel
import com.example.dietapp.ui.login.LoginViewModel
import com.example.dietapp.ui.main.MainViewModel
import com.example.dietapp.ui.recordmeal.RecordMealViewModel
import com.example.dietapp.ui.register.RegisterViewModel
import com.example.dietapp.ui.friends.FriendsViewModel
import com.example.dietapp.ui.goals.GoalsViewModel
import com.example.dietapp.ui.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FriendsViewModel::class)
    abstract fun provideFriendsViewModel(friendsViewModel: FriendsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateMealViewModel::class)
    abstract fun provideCreateMealViewModel(createMealViewModel: CreateMealViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GoalsViewModel::class)
    abstract fun provideGoalsViewModel(goalsMealViewModel: GoalsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun provideProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecordMealViewModel::class)
    abstract fun provideRecordMealViewModel(recordMealViewModel: RecordMealViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun provideHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun provideLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun provideRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel
}