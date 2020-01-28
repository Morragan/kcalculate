package com.example.dietapp.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.api.exceptions.NotAuthorizedException
import com.example.dietapp.db.repositories.AccountRepository
import com.example.dietapp.ui.calculatenutrientgoals.NutrientGoalsData
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val accountRepository: AccountRepository) :
    ViewModel() {

    val user = accountRepository.user
    val loggedIn = accountRepository.loggedIn
    val isLoadingDialogVisible = MutableLiveData<Boolean>()

    fun toggleAccountIsPrivate() = viewModelScope.launch {
        try {
            accountRepository.changeAccountIsPrivate(!user.value!!.isPrivate)
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateNutrientGoals(nutrientGoals: NutrientGoalsData) = viewModelScope.launch {
        try {
            accountRepository.updateNutrientGoals(nutrientGoals)
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}