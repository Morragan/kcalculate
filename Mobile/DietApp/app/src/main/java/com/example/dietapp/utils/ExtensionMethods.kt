package com.example.dietapp.utils

import android.app.Activity
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dietapp.models.dto.TokenDTO
import com.example.dietapp.models.dto.UserDTO
import com.example.dietapp.models.entity.User
import java.util.*

fun <T> MutableLiveData<MutableList<T>>.addItem(item: T) {
    val list = this.value ?: mutableListOf()
    list.add(item)
    this.value = list
}

fun <T> MutableLiveData<MutableList<T>>.removeItem(item: T) {
    val list = this.value
    list?.remove(item)
    this.value = list
}

fun SharedPreferences.saveToken(token: TokenDTO) {
    with(this.edit()) {
        putString(Constants.sharedPreferencesKeyAccessToken, token.accessToken)
        putString(Constants.sharedPreferencesKeyRefreshToken, token.refreshToken)
        putLong(Constants.sharedPreferencesKeyTokenExpiration, token.expiration)
        apply()
    }
}

fun SharedPreferences.getToken(): TokenDTO = TokenDTO(
    getString(Constants.sharedPreferencesKeyAccessToken, "")!!,
    getString(Constants.sharedPreferencesKeyRefreshToken, "")!!,
    getLong(Constants.sharedPreferencesKeyTokenExpiration, -1)
)

fun SharedPreferences.removeToken() {
    with(this.edit()) {
        remove(Constants.sharedPreferencesKeyAccessToken)
        remove(Constants.sharedPreferencesKeyRefreshToken)
        remove(Constants.sharedPreferencesKeyTokenExpiration)
        apply()
    }
}


fun SharedPreferences.saveUser(user: UserDTO) {
    with(this.edit()) {
        putInt(Constants.sharedPreferencesKeyUserId, user.id)
        putString(Constants.sharedPreferencesKeyUserNickname, user.nickname)
        putString(Constants.sharedPreferencesKeyUserEmail, user.email)
        putString(Constants.sharedPreferencesKeyUserAvatarLink, user.avatarLink)
        putLong(Constants.sharedPreferencesKeyUserJoinDate, user.joinDate.time)
        putInt(Constants.sharedPreferencesKeyUserPoints, user.points)
        putBoolean(Constants.sharedPreferencesKeyUserIsEmailConfirmed, user.isEmailConfirmed)
        putInt(Constants.sharedPreferencesKeyUserCalorieLimit, user.calorieLimit)
        putInt(Constants.sharedPreferencesKeyUserCalorieLimitLower, user.calorieLimitLower)
        putInt(Constants.sharedPreferencesKeyUserCalorieLimitUpper, user.calorieLimitUpper)
        putInt(Constants.sharedPreferencesKeyUserCarbsLimit, user.carbsLimit)
        putInt(Constants.sharedPreferencesKeyUserCarbsLimitLower, user.carbsLimitLower)
        putInt(Constants.sharedPreferencesKeyUserCarbsLimitUpper, user.carbsLimitUpper)
        putInt(Constants.sharedPreferencesKeyUserFatLimit, user.fatLimit)
        putInt(Constants.sharedPreferencesKeyUserFatLimitLower, user.fatLimitLower)
        putInt(Constants.sharedPreferencesKeyUserFatLimitUpper, user.fatLimitUpper)
        putInt(Constants.sharedPreferencesKeyUserProteinLimit, user.proteinLimit)
        putInt(Constants.sharedPreferencesKeyUserProteinLimitLower, user.proteinLimitLower)
        putInt(Constants.sharedPreferencesKeyUserProteinLimitUpper, user.proteinLimitUpper)
        putBoolean(Constants.sharedPreferencesKeyUserIsPrivate, user.isPrivate)
        apply()
    }
}

fun SharedPreferences.getUser() = User(
    getInt(Constants.sharedPreferencesKeyUserId, 0),
    getString(Constants.sharedPreferencesKeyUserNickname, "")!!,
    getString(Constants.sharedPreferencesKeyUserEmail, "")!!,
    getString(Constants.sharedPreferencesKeyUserAvatarLink, "")!!,
    Date(getLong(Constants.sharedPreferencesKeyUserJoinDate, 0)),
    getInt(Constants.sharedPreferencesKeyUserPoints, 0),
    getBoolean(Constants.sharedPreferencesKeyUserIsEmailConfirmed, false),
    getInt(Constants.sharedPreferencesKeyUserCalorieLimit, 0),
    getInt(Constants.sharedPreferencesKeyUserCalorieLimitLower, 0),
    getInt(Constants.sharedPreferencesKeyUserCalorieLimitUpper, 0),
    getInt(Constants.sharedPreferencesKeyUserCarbsLimit, 0),
    getInt(Constants.sharedPreferencesKeyUserCarbsLimitLower, 0),
    getInt(Constants.sharedPreferencesKeyUserCarbsLimitUpper, 0),
    getInt(Constants.sharedPreferencesKeyUserFatLimit, 0),
    getInt(Constants.sharedPreferencesKeyUserFatLimitLower, 0),
    getInt(Constants.sharedPreferencesKeyUserFatLimitUpper, 0),
    getInt(Constants.sharedPreferencesKeyUserProteinLimit, 0),
    getInt(Constants.sharedPreferencesKeyUserProteinLimitLower, 0),
    getInt(Constants.sharedPreferencesKeyUserProteinLimitUpper, 0),
    getBoolean(Constants.sharedPreferencesKeyUserIsPrivate, false)
)

fun SharedPreferences.removeUser(){
    with(this.edit()){
        remove(Constants.sharedPreferencesKeyUserId)
        remove(Constants.sharedPreferencesKeyUserNickname)
        remove(Constants.sharedPreferencesKeyUserEmail)
        remove(Constants.sharedPreferencesKeyUserAvatarLink)
        remove(Constants.sharedPreferencesKeyUserJoinDate)
        remove(Constants.sharedPreferencesKeyUserPoints)
        remove(Constants.sharedPreferencesKeyUserIsEmailConfirmed)
        remove(Constants.sharedPreferencesKeyUserCalorieLimit)
        remove(Constants.sharedPreferencesKeyUserCalorieLimitLower)
        remove(Constants.sharedPreferencesKeyUserCalorieLimitUpper)
        remove(Constants.sharedPreferencesKeyUserCarbsLimit)
        remove(Constants.sharedPreferencesKeyUserCarbsLimitLower)
        remove(Constants.sharedPreferencesKeyUserCarbsLimitUpper)
        remove(Constants.sharedPreferencesKeyUserFatLimit)
        remove(Constants.sharedPreferencesKeyUserFatLimitLower)
        remove(Constants.sharedPreferencesKeyUserFatLimitUpper)
        remove(Constants.sharedPreferencesKeyUserProteinLimit)
        remove(Constants.sharedPreferencesKeyUserProteinLimitLower)
        remove(Constants.sharedPreferencesKeyUserProteinLimitUpper)
        apply()
    }
}

fun Activity.hideKeyboard() {
    val inputManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val focusedView = currentFocus ?: View(this)
    inputManager.hideSoftInputFromWindow(focusedView.windowToken, 0)
}