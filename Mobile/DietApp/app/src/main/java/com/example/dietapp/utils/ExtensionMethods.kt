package com.example.dietapp.utils

import android.app.Activity
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.MutableLiveData
import com.example.dietapp.models.dto.TokenDTO
import com.example.dietapp.models.dto.UserDTO
import com.example.dietapp.models.entity.Goal
import com.example.dietapp.models.entity.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

fun <T> MutableLiveData<MutableList<T>>.clear() {
    val list = this.value
    list?.clear()
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
        putInt(Constants.sharedPreferencesKeyUserGoalPoints, user.goalPoints)
        putBoolean(Constants.sharedPreferencesKeyUserIsEmailConfirmed, user.isEmailConfirmed)
        putFloat(Constants.sharedPreferencesKeyUserCalorieLimit, user.calorieLimit)
        putFloat(Constants.sharedPreferencesKeyUserCalorieLimitLower, user.calorieLimitLower)
        putFloat(Constants.sharedPreferencesKeyUserCalorieLimitUpper, user.calorieLimitUpper)
        putFloat(Constants.sharedPreferencesKeyUserCarbsLimit, user.carbsLimit)
        putFloat(Constants.sharedPreferencesKeyUserCarbsLimitLower, user.carbsLimitLower)
        putFloat(Constants.sharedPreferencesKeyUserCarbsLimitUpper, user.carbsLimitUpper)
        putFloat(Constants.sharedPreferencesKeyUserFatLimit, user.fatLimit)
        putFloat(Constants.sharedPreferencesKeyUserFatLimitLower, user.fatLimitLower)
        putFloat(Constants.sharedPreferencesKeyUserFatLimitUpper, user.fatLimitUpper)
        putFloat(Constants.sharedPreferencesKeyUserProteinLimit, user.proteinLimit)
        putFloat(Constants.sharedPreferencesKeyUserProteinLimitLower, user.proteinLimitLower)
        putFloat(Constants.sharedPreferencesKeyUserProteinLimitUpper, user.proteinLimitUpper)
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
    getInt(Constants.sharedPreferencesKeyUserGoalPoints, 0),
    getBoolean(Constants.sharedPreferencesKeyUserIsEmailConfirmed, false),
    getFloat(Constants.sharedPreferencesKeyUserCalorieLimit, 0f),
    getFloat(Constants.sharedPreferencesKeyUserCalorieLimitLower, 0f),
    getFloat(Constants.sharedPreferencesKeyUserCalorieLimitUpper, 0f),
    getFloat(Constants.sharedPreferencesKeyUserCarbsLimit, 0f),
    getFloat(Constants.sharedPreferencesKeyUserCarbsLimitLower, 0f),
    getFloat(Constants.sharedPreferencesKeyUserCarbsLimitUpper, 0f),
    getFloat(Constants.sharedPreferencesKeyUserFatLimit, 0f),
    getFloat(Constants.sharedPreferencesKeyUserFatLimitLower, 0f),
    getFloat(Constants.sharedPreferencesKeyUserFatLimitUpper, 0f),
    getFloat(Constants.sharedPreferencesKeyUserProteinLimit, 0f),
    getFloat(Constants.sharedPreferencesKeyUserProteinLimitLower, 0f),
    getFloat(Constants.sharedPreferencesKeyUserProteinLimitUpper, 0f),
    getBoolean(Constants.sharedPreferencesKeyUserIsPrivate, false)
)

fun SharedPreferences.removeUser() {
    with(this.edit()) {
        remove(Constants.sharedPreferencesKeyUserId)
        remove(Constants.sharedPreferencesKeyUserNickname)
        remove(Constants.sharedPreferencesKeyUserEmail)
        remove(Constants.sharedPreferencesKeyUserAvatarLink)
        remove(Constants.sharedPreferencesKeyUserJoinDate)
        remove(Constants.sharedPreferencesKeyUserPoints)
        remove(Constants.sharedPreferencesKeyUserGoalPoints)
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

fun SharedPreferences.saveGoal(goal: Goal) {
    with(this.edit()) {
        putFloat(Constants.sharedPreferencesKeyGoalCalorieLimit, goal.calorieLimit)
        putFloat(Constants.sharedPreferencesKeyGoalCalorieLimitLower, goal.calorieLimitLower)
        putFloat(Constants.sharedPreferencesKeyGoalCalorieLimitUpper, goal.calorieLimitUpper)
        putFloat(Constants.sharedPreferencesKeyGoalCarbsLimit, goal.carbsLimit)
        putFloat(Constants.sharedPreferencesKeyGoalCarbsLimitLower, goal.carbsLimitLower)
        putFloat(Constants.sharedPreferencesKeyGoalCarbsLimitUpper, goal.carbsLimitUpper)
        putFloat(Constants.sharedPreferencesKeyGoalFatLimit, goal.fatLimit)
        putFloat(Constants.sharedPreferencesKeyGoalFatLimitLower, goal.fatLimitLower)
        putFloat(Constants.sharedPreferencesKeyGoalFatLimitUpper, goal.fatLimitUpper)
        putFloat(Constants.sharedPreferencesKeyGoalProteinLimit, goal.proteinLimit)
        putFloat(Constants.sharedPreferencesKeyGoalProteinLimitLower, goal.proteinLimitLower)
        putFloat(Constants.sharedPreferencesKeyGoalProteinLimitUpper, goal.proteinLimitUpper)
        putInt(Constants.sharedPreferencesKeyGoalID, goal.goalID)
        putString(
            Constants.sharedPreferencesKeyGoalParticipatingFriends,
            Gson().toJson(goal.participatingFriends)
        )
        putLong(Constants.sharedPreferencesKeyGoalStartDate, goal.startDate.time)
        putInt(Constants.sharedPreferencesKeyGoalStatus, goal.status)
        putFloat(Constants.sharedPreferencesKeyGoalWeightGoal, goal.weightGoal)
        apply()
    }
}

fun SharedPreferences.getGoal(): Goal {
    val participatingFriendsJSON =
        getString(Constants.sharedPreferencesKeyGoalParticipatingFriends, "")
    val participatingFriends = if (participatingFriendsJSON.isNullOrBlank()) emptyList<Int>()
    else Gson().fromJson(participatingFriendsJSON, object : TypeToken<List<Int>>() {}.type)

    return Goal(
        getFloat(Constants.sharedPreferencesKeyGoalCalorieLimit, 0f),
        getFloat(Constants.sharedPreferencesKeyGoalCalorieLimitLower, 0f),
        getFloat(Constants.sharedPreferencesKeyGoalCalorieLimitUpper, 0f),
        getFloat(Constants.sharedPreferencesKeyGoalCarbsLimit, 0f),
        getFloat(Constants.sharedPreferencesKeyGoalCarbsLimitLower, 0f),
        getFloat(Constants.sharedPreferencesKeyGoalCarbsLimitUpper, 0f),
        getFloat(Constants.sharedPreferencesKeyGoalFatLimit, 0f),
        getFloat(Constants.sharedPreferencesKeyGoalFatLimitLower, 0f),
        getFloat(Constants.sharedPreferencesKeyGoalFatLimitUpper, 0f),
        getFloat(Constants.sharedPreferencesKeyGoalProteinLimit, 0f),
        getFloat(Constants.sharedPreferencesKeyGoalProteinLimitLower, 0f),
        getFloat(Constants.sharedPreferencesKeyGoalProteinLimitUpper, 0f),
        getInt(Constants.sharedPreferencesKeyGoalID, 0),
        participatingFriends,
        Date(getLong(Constants.sharedPreferencesKeyGoalStartDate, 0)),
        getInt(Constants.sharedPreferencesKeyGoalStatus, 0),
        getFloat(Constants.sharedPreferencesKeyGoalWeightGoal, 0f)
    )
}

fun SharedPreferences.removeGoal() {
    with(this.edit()) {
        remove(Constants.sharedPreferencesKeyGoalCalorieLimit)
        remove(Constants.sharedPreferencesKeyGoalCalorieLimitLower)
        remove(Constants.sharedPreferencesKeyGoalCalorieLimitUpper)
        remove(Constants.sharedPreferencesKeyGoalCarbsLimit)
        remove(Constants.sharedPreferencesKeyGoalCarbsLimitLower)
        remove(Constants.sharedPreferencesKeyGoalCarbsLimitUpper)
        remove(Constants.sharedPreferencesKeyGoalFatLimit)
        remove(Constants.sharedPreferencesKeyGoalFatLimitLower)
        remove(Constants.sharedPreferencesKeyGoalFatLimitUpper)
        remove(Constants.sharedPreferencesKeyGoalProteinLimit)
        remove(Constants.sharedPreferencesKeyGoalProteinLimitLower)
        remove(Constants.sharedPreferencesKeyGoalProteinLimitUpper)
        remove(Constants.sharedPreferencesKeyGoalID)
        remove(Constants.sharedPreferencesKeyGoalParticipatingFriends)
        remove(Constants.sharedPreferencesKeyGoalStartDate)
        remove(Constants.sharedPreferencesKeyGoalStatus)
        remove(Constants.sharedPreferencesKeyGoalWeightGoal)
        apply()
    }
}

fun Activity.hideKeyboard() {
    val inputManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val focusedView = currentFocus ?: View(this)
    inputManager.hideSoftInputFromWindow(focusedView.windowToken, 0)
}