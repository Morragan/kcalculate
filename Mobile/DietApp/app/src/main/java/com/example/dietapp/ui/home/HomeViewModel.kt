package com.example.dietapp.ui.home

import androidx.lifecycle.*
import com.example.dietapp.R
import com.example.dietapp.api.exceptions.NotAuthorizedException
import com.example.dietapp.db.repositories.AccountRepository
import com.example.dietapp.db.repositories.MealEntriesRepository
import com.example.dietapp.utils.Methods
import com.example.dietapp.utils.StringResourcesProvider
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val mealEntriesRepository: MealEntriesRepository,
    private val accountRepository: AccountRepository,
    private val stringResourcesProvider: StringResourcesProvider
) : ViewModel() {
    private val selectedDate = MutableLiveData<Date>(Date())
    private val mealEntries = mealEntriesRepository.allMealEntries
    val user = accountRepository.user
    val loggedIn = accountRepository.loggedIn

    val selectedDateString = Transformations.map(selectedDate) {
        when {
            // selected date is today
            Methods.datesAreTheSameDay(
                it,
                Date()
            ) -> stringResourcesProvider.getString(R.string.today)
            // selected date is yesterday
            Methods.datesAreTheSameDay(
                it,
                Methods.offsetDate(Date(), -1)
            ) ->
                stringResourcesProvider.getString(R.string.yesterday)
            // selected date is some other date
            else -> {
                val formatter = SimpleDateFormat("EEE, d MMM yy", Locale.getDefault())
                formatter.format(it)
            }
        }
    }
    val selectedDateMealsSummary = MediatorLiveData<DaySummaryData>()

    init {
        selectedDateMealsSummary.apply {
            addSource(selectedDate) {
                selectedDateMealsSummary.value = updateDaySummary()
            }
            addSource(mealEntries) {
                selectedDateMealsSummary.value = updateDaySummary()
            }
        }
    }

    fun onDateButtonBackward() {
        selectedDate.apply {
            value = Methods.offsetDate(value!!, -1)
        }
    }

    fun onDateButtonForward() {
        selectedDate.apply {
            value = Methods.offsetDate(value!!, 1)
        }
    }

    fun fetchMealEntries() = viewModelScope.launch {
        try{
            mealEntriesRepository.fetchMealEntries()
        }
        catch (e: NotAuthorizedException){
            accountRepository.logout()
        }
    }

    private fun updateDaySummary(): DaySummaryData? {
        val selectedDateEntries = mealEntries.value?.filter { entry ->
            Methods.datesAreTheSameDay(entry.date, selectedDate.value!!)
        } ?: return null

        user.value ?: return null

        val kcalEaten = selectedDateEntries.sumBy { entry -> entry.kcal }
        val carbsEaten = selectedDateEntries.sumBy { entry -> entry.nutrients.carbsGram }
        val fatEaten = selectedDateEntries.sumBy { entry -> entry.nutrients.fatGram }
        val proteinEaten = selectedDateEntries.sumBy { entry -> entry.nutrients.proteinGram }

        val kcalProgress = kcalEaten.toFloat() / user.value!!.calorieLimit * 100f
        val carbsProgress = carbsEaten.toFloat() / user.value!!.carbsLimit * 100f
        val fatProgress = fatEaten.toFloat() / user.value!!.fatLimit * 100f
        val proteinProgress = proteinEaten.toFloat() / user.value!!.proteinLimit * 100f

        val kcalLeft =
            if (user.value!!.calorieLimit - kcalEaten < 0) 0 else user.value!!.calorieLimit - kcalEaten
        val carbsLeft =
            if (user.value!!.carbsLimit - carbsEaten < 0) 0 else user.value!!.carbsLimit - carbsEaten
        val fatLeft =
            if (user.value!!.fatLimit - fatEaten < 0) 0 else user.value!!.fatLimit - fatEaten
        val proteinLeft =
            if (user.value!!.proteinLimit - proteinEaten < 0) 0 else user.value!!.proteinLimit - proteinEaten

        val kcalProgressColorId = when {
            kcalEaten < user.value!!.calorieLimitLower -> R.color.warning
            kcalEaten > user.value!!.calorieLimitUpper -> R.color.error
            else -> R.color.success
        }
        val carbsProgressColorId = when {
            carbsEaten < user.value!!.carbsLimitLower -> R.color.warning
            carbsEaten > user.value!!.carbsLimitUpper -> R.color.error
            else -> R.color.success
        }
        val fatProgressColorId = when {
            fatEaten < user.value!!.fatLimitLower -> R.color.warning
            fatEaten > user.value!!.fatLimitUpper -> R.color.error
            else -> R.color.success
        }
        val proteinProgressColorId = when {
            proteinEaten < user.value!!.proteinLimitLower -> R.color.warning
            proteinEaten > user.value!!.proteinLimitUpper -> R.color.error
            else -> R.color.success
        }


        return DaySummaryData(
            kcalEaten,
            user.value!!.calorieLimit,
            kcalLeft,
            kcalProgress,
            kcalProgressColorId,
            carbsEaten,
            user.value!!.carbsLimit,
            carbsLeft,
            carbsProgress.toInt(),
            carbsProgressColorId,
            fatEaten,
            user.value!!.fatLimit,
            fatLeft,
            fatProgress.toInt(),
            fatProgressColorId,
            proteinEaten,
            user.value!!.proteinLimit,
            proteinLeft,
            proteinProgress.toInt(),
            proteinProgressColorId
        )
    }

    data class DaySummaryData(
        val kcalEaten: Int,
        val kcalGoal: Int,
        val kcalLeft: Int,
        val kcalProgress: Float,
        val kcalProgressColorId: Int,

        val carbsEaten: Int,
        val carbsGoal: Int,
        val carbsLeft: Int,
        val carbsProgress: Int,
        val carbsProgressColorId: Int,

        val fatEaten: Int,
        val fatGoal: Int,
        val fatLeft: Int,
        val fatProgress: Int,
        val fatProgressColorId: Int,

        val proteinEaten: Int,
        val proteinGoal: Int,
        val proteinLeft: Int,
        val proteinProgress: Int,
        val proteinProgressColorId: Int

    )
}