package com.example.dietapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.db.repositories.AccountRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val accountRepository: AccountRepository) :
    ViewModel() {
    val userLoggedIn = accountRepository.loggedIn

    fun checkUserLoggedIn() = viewModelScope.launch {
        accountRepository.checkUserLoggedIn()
    }
}