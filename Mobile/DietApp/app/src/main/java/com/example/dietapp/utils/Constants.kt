package com.example.dietapp.utils

object Constants {
    const val apiBaseUrl = "https://192.168.8.100:44332/api/"

    //Shared preferences keys
    const val sharedPreferencesFileKey = "com.example.dietapp.utils.constants.sharedprefsfilekey"
    const val sharedPreferencesKeyAccessToken = "com.example.dietapp.utils.constants.preferenceaccesstoken"
    const val sharedPreferencesKeyRefreshToken = "com.example.dietapp.utils.constants.preferencerefreshtoken"
    const val sharedPreferencesKeyTokenExpiration = "com.example.dietapp.utils.constants.preferencetokenexpiration"
    const val sharedPreferencesKeyUserId = "com.example.dietapp.utils.constants.preference.preferenceuserid"
    const val sharedPreferencesKeyUserNickname = "com.example.dietapp.utils.constants.preferenceusernickname"
    const val sharedPreferencesKeyUserEmail = "com.example.dietapp.utils.constants.preferenceuseremail"
    const val sharedPreferencesKeyUserAvatarLink = "com.example.dietapp.utils.constants.preferenceuseravatarlink"
    const val sharedPreferencesKeyUserJoinDate = "com.example.dietapp.utils.constants.preferenceuser"
    const val sharedPreferencesKeyUserPoints = "com.example.dietapp.utils.constants.preferenceuserpoints"
    const val sharedPreferencesKeyUserIsEmailConfirmed = "com.example.dietapp.utils.constants.preferenceuserisemailconfirmed"
    const val sharedPreferencesKeyUserCalorieLimit = "com.example.dietapp.utils.constants.preferenceusercalorielimit"
    const val sharedPreferencesKeyUserCalorieLimitLower = "com.example.dietapp.utils.constants.preferenceusercalorielimitlower"
    const val sharedPreferencesKeyUserCalorieLimitUpper = "com.example.dietapp.utils.constants.preferenceusercalorielimitupper"
    const val sharedPreferencesKeyUserCarbsLimit = "com.example.dietapp.utils.constants.preferenceusercarbslimit"
    const val sharedPreferencesKeyUserCarbsLimitLower = "com.example.dietapp.utils.constants.preferenceusercarbslimitlower"
    const val sharedPreferencesKeyUserCarbsLimitUpper = "com.example.dietapp.utils.constants.preferenceusercarbslimitupper"
    const val sharedPreferencesKeyUserFatLimit = "com.example.dietapp.utils.constants.preferenceuserfatlimit"
    const val sharedPreferencesKeyUserFatLimitLower = "com.example.dietapp.utils.constants.preferenceuserfatlimitlower"
    const val sharedPreferencesKeyUserFatLimitUpper = "com.example.dietapp.utils.constants.preferenceuserfatlimitupper"
    const val sharedPreferencesKeyUserProteinLimit = "com.example.dietapp.utils.constants.preferenceuserproteinlimit"
    const val sharedPreferencesKeyUserProteinLimitLower = "com.example.dietapp.utils.constants.preferenceuserproteinlimitlower"
    const val sharedPreferencesKeyUserProteinLimitUpper = "com.example.dietapp.utils.constants.preferenceuserproteinlimitupper"
    const val sharedPreferencesKeyUserIsPrivate = "com.example.dietapp.utils.constants.preferenceuserisprivate"

    // Intent keys
    const val intentKeyRegisterToLoginNickname =
        "com.example.dietapp.utils.constants.intentkeyregistertologinnickname"
    const val intentKeyRecordMealToCreateMealBarcode = "com.example.dietapp.utils.constants.intentkeyrecordmealtocreatemealincludebarcode"

    // Activity request codes
    const val requestCodeCreateMeal = 100
    const val requestCodeScanBarcode = 49374
    const val requestCodeNoInternet = 99
}