package com.example.dietapp.utils

object Constants {
    const val apiBaseUrl = "https://192.168.8.100:44332/api/"

    //Shared preferences keys
    const val sharedPreferencesFileKey = "com.example.dietapp.utils.constants.sharedprefsfilekey"
    const val sharedPreferencesKeyAccessToken = "com.example.dietapp.utils.constants.preferenceaccesstoken"
    const val sharedPreferencesKeyRefreshToken = "com.example.dietapp.utils.constants.preferencerefreshtoken"
    const val sharedPreferencesKeyTokenExpiration = "com.example.dietapp.utils.constants.preferencetokenexpiration"

    // Intent keys
    const val intentKeyRegisterToLoginNickname =
        "com.example.dietapp.utils.constants.intentkeyregistertologinnickname"

    // Activity request codes
    const val requestCodeCreateMeal = 100
    const val requestCodeScanBarcode = 49374
}