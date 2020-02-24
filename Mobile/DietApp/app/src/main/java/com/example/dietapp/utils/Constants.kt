package com.example.dietapp.utils

object Constants {
    const val apiBaseUrl = "https://dietapp20200221103617.azurewebsites.net/api/"

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
    const val sharedPreferencesKeyUserGoalPoints = "com.example.dietapp.utils.constants.preferenceusergoalpoints"
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
    const val sharedPreferencesKeyGoalCalorieLimit = "com.example.dietapp.utils.constants.preferencegoalcalorielimit"
    const val sharedPreferencesKeyGoalCalorieLimitLower = "com.example.dietapp.utils.constants.preferencegoalcalorielimitlower"
    const val sharedPreferencesKeyGoalCalorieLimitUpper = "com.example.dietapp.utils.constants.preferencegoalcalorielimitupper"
    const val sharedPreferencesKeyGoalCarbsLimit = "com.example.dietapp.utils.constants.preferencegoalcarbslimit"
    const val sharedPreferencesKeyGoalCarbsLimitLower = "com.example.dietapp.utils.constants.preferencegoalcarbslimitlower"
    const val sharedPreferencesKeyGoalCarbsLimitUpper = "com.example.dietapp.utils.constants.preferencegoalcarbslimitupper"
    const val sharedPreferencesKeyGoalFatLimit = "com.example.dietapp.utils.constants.preferencegoalfatlimit"
    const val sharedPreferencesKeyGoalFatLimitLower = "com.examplse.dietapp.utils.constants.preferencegoalfatlimitlower"
    const val sharedPreferencesKeyGoalFatLimitUpper = "com.example.dietapp.utils.constants.preferencegoalfatlimitupper"
    const val sharedPreferencesKeyGoalProteinLimit = "com.example.dietapp.utils.constants.preferencegoalproteinlimit"
    const val sharedPreferencesKeyGoalProteinLimitLower = "com.example.dietapp.utils.constants.preferencegoalproteinlimitlower"
    const val sharedPreferencesKeyGoalProteinLimitUpper = "com.example.dietapp.utils.constants.preferencegoalproteinlimitupper"
    const val sharedPreferencesKeyGoalID = "com.example.dietapp.utils.constants.preferencegoalid"
    const val sharedPreferencesKeyGoalParticipatingFriends = "com.example.dietapp.utils.constants.preferencegoalparticipatingfriends"
    const val sharedPreferencesKeyGoalStartDate = "com.example.dietapp.utils.constants.preferencegoalstartdate"
    const val sharedPreferencesKeyGoalStatus = "com.example.dietapp.utils.constants.preferencegoalstatus"
    const val sharedPreferencesKeyGoalWeightGoal = "com.example.dietapp.utils.constants.preferencegoalweightgoal"


        // Intent keys
    const val intentKeyCalculateNutrientGoalsResult = "com.example.dietapp.utils.constants.intentkeycalculatenutrientgoalsresult"
    const val intentKeyRegisterToLoginNickname =
        "com.example.dietapp.utils.constants.intentkeyregistertologinnickname"
    const val intentKeyRecordMealToCreateMealBarcode = "com.example.dietapp.utils.constants.intentkeyrecordmealtocreatemealincludebarcode"

    // Activity request codes
    const val requestCodeCreateMeal = 100
    const val requestCodeScanBarcode = 49374
    const val requestCodeNoInternet = 99
    const val requestCodeCalculateGoals = 98
}