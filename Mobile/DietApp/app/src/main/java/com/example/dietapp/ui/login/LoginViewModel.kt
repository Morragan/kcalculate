package com.example.dietapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.db.repositories.AccountRepository
import com.example.dietapp.models.dto.LoginDTO
import com.example.dietapp.utils.ButtonState
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val accountRepository: AccountRepository) :
    ViewModel() {
    val isLoggedIn = accountRepository.loggedIn
    val hasInternetConnection = MutableLiveData<Boolean>(true)
    val loginButtonState = MutableLiveData<ButtonState>(
        ButtonState.DEFAULT
    )

    fun login(loginDTO: LoginDTO) = viewModelScope.launch {
        try {
            loginButtonState.postValue(ButtonState.LOADING)
            accountRepository.login(loginDTO)
        } catch (e: ConnectException) {
            loginButtonState.postValue(ButtonState.FAIL)
            hasInternetConnection.value = false
        } catch (e: Exception) {
            loginButtonState.postValue(ButtonState.FAIL)
            e.printStackTrace()
        }
    }
}