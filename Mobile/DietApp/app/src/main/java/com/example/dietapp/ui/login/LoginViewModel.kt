package com.example.dietapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.db.repositories.AccountRepository
import com.example.dietapp.models.dto.LoginDTO
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val accountRepository: AccountRepository) :
    ViewModel() {
    val isLoggedIn = accountRepository.loggedIn

    fun login(loginDTO: LoginDTO) = viewModelScope.launch {
        accountRepository.login(loginDTO)
    }
}