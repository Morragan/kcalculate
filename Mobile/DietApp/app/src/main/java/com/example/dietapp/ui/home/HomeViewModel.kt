package com.example.dietapp.ui.home

import androidx.lifecycle.*
import com.example.dietapp.R
import com.example.dietapp.api.exceptions.NotAuthorizedException
import com.example.dietapp.db.repositories.AccountRepository
import com.example.dietapp.db.repositories.MealEntriesRepository
import com.example.dietapp.utils.Methods.datesAreTheSameDay
import com.example.dietapp.utils.Methods.offsetDate
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
            datesAreTheSameDay(
                it,
                Date()
            ) -> stringResourcesProvider.getString(R.string.today)
            // selected date is yesterday
            datesAreTheSameDay(
                it,
                offsetDate(Date(), -1)
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
        if (user.value != null && !datesAreTheSameDay(user.value!!.joinDate, selectedDate.value!!))
            selectedDate.apply { value = offsetDate(value!!, -1) }
    }

    fun onDateButtonForward() {
        if (!datesAreTheSameDay(selectedDate.value!!, Date()))
            selectedDate.apply { value = offsetDate(value!!, 1) }
    }

    fun fetchMealEntries() = viewModelScope.launch {
        try {
            mealEntriesRepository.fetchMealEntries()
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun fetchUserData() = viewModelScope.launch {
        try {
            accountRepository.fetchUserData()
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateDaySummary(): DaySummaryData? {
        val selectedDateEntries = mealEntries.value?.filter { entry ->
            datesAreTheSameDay(entry.date, selectedDate.value!!)
        } ?: return null

        if (user.value?.email.isNullOrBlank()) return null

        val kcalEaten = selectedDateEntries.sumByDouble { entry -> entry.kcal }
        val carbsEaten = selectedDateEntries.sumByDouble { entry -> entry.nutrients.carbs }
        val fatEaten = selectedDateEntries.sumByDouble { entry -> entry.nutrients.fat }
        val proteinEaten = selectedDateEntries.sumByDouble { entry -> entry.nutrients.protein }

        val kcalProgress = kcalEaten.toFloat() / user.value!!.calorieLimit * 100f
        val carbsProgress = carbsEaten.toFloat() / user.value!!.carbsLimit * 100f
        val fatProgress = fatEaten.toFloat() / user.value!!.fatLimit * 100f
        val proteinProgress = proteinEaten.toFloat() / user.value!!.proteinLimit * 100f

        val kcalLeft: Double =
            if (user.value!!.calorieLimit - kcalEaten < 0) 0.0 else user.value!!.calorieLimit - kcalEaten
        val carbsLeft: Double =
            if (user.value!!.carbsLimit - carbsEaten < 0) 0.0 else user.value!!.carbsLimit - carbsEaten
        val fatLeft: Double =
            if (user.value!!.fatLimit - fatEaten < 0) 0.0 else user.value!!.fatLimit - fatEaten
        val proteinLeft: Double =
            if (user.value!!.proteinLimit - proteinEaten < 0) 0.0 else user.value!!.proteinLimit - proteinEaten

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
            kcalEaten.toInt(),
            user.value!!.calorieLimit,
            kcalLeft.toInt(),
            kcalProgress,
            kcalProgressColorId,
            carbsEaten.toInt(),
            user.value!!.carbsLimit,
            carbsLeft.toInt(),
            carbsProgress.toInt(),
            carbsProgressColorId,
            fatEaten.toInt(),
            user.value!!.fatLimit,
            fatLeft.toInt(),
            fatProgress.toInt(),
            fatProgressColorId,
            proteinEaten.toInt(),
            user.value!!.proteinLimit,
            proteinLeft.toInt(),
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