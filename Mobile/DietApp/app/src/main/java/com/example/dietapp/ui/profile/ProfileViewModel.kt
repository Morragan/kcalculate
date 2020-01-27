package com.example.dietapp.ui.profile

import androidx.lifecycle.ViewModel
import com.example.dietapp.db.repositories.AccountRepository
import javax.inject.Inject

class ProfileViewModel @Inject constructor(accountRepository: AccountRepository): ViewModel() {

    val user = accountRepository.user
    val loggedIn = accountRepository.loggedIn
}