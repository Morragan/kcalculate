package com.example.dietapp.ui.home

import android.text.format.DateUtils
import androidx.lifecycle.*
import com.example.dietapp.R
import com.example.dietapp.api.exceptions.NotAuthorizedException
import com.example.dietapp.db.repositories.AccountRepository
import com.example.dietapp.db.repositories.GoalRepository
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
    private val goalRepository: GoalRepository,
    private val accountRepository: AccountRepository,
    private val stringResourcesProvider: StringResourcesProvider
) : ViewModel() {

    private val selectedDate = MutableLiveData<Date>(Date())
    private val mealEntries = mealEntriesRepository.allMealEntries
    val goal = goalRepository.goal
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
            addSource(goal) {
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
        if (goal.value == null) return null

        val calorieLimit =
            if (goal.value != null && goal.value!!.startDate >= selectedDate.value || DateUtils.isToday(goal.value!!.startDate.time)) goal.value!!.calorieLimit else user.value!!.calorieLimit
        val calorieLimitLower =
            if (goal.value != null && goal.value!!.startDate >= selectedDate.value || DateUtils.isToday(goal.value!!.startDate.time)) goal.value!!.calorieLimitLower else user.value!!.calorieLimitLower
        val calorieLimitUpper =
            if (goal.value != null && goal.value!!.startDate >= selectedDate.value || DateUtils.isToday(goal.value!!.startDate.time)) goal.value!!.calorieLimitUpper else user.value!!.calorieLimitUpper
        val carbsLimit =
            if (goal.value != null && goal.value!!.startDate >= selectedDate.value || DateUtils.isToday(goal.value!!.startDate.time)) goal.value!!.carbsLimit else user.value!!.carbsLimit
        val carbsLimitLower =
            if (goal.value != null && goal.value!!.startDate >= selectedDate.value || DateUtils.isToday(goal.value!!.startDate.time)) goal.value!!.carbsLimitLower else user.value!!.carbsLimitLower
        val carbsLimitUpper =
            if (goal.value != null && goal.value!!.startDate >= selectedDate.value || DateUtils.isToday(goal.value!!.startDate.time)) goal.value!!.carbsLimitUpper else user.value!!.carbsLimitUpper
        val fatLimit =
            if (goal.value != null && goal.value!!.startDate >= selectedDate.value || DateUtils.isToday(goal.value!!.startDate.time)) goal.value!!.fatLimit else user.value!!.fatLimit
        val fatLimitLower =
            if (goal.value != null && goal.value!!.startDate >= selectedDate.value || DateUtils.isToday(goal.value!!.startDate.time)) goal.value!!.fatLimitLower else user.value!!.fatLimitLower
        val fatLimitUpper =
            if (goal.value != null && goal.value!!.startDate >= selectedDate.value || DateUtils.isToday(goal.value!!.startDate.time)) goal.value!!.fatLimitUpper else user.value!!.fatLimitUpper
        val proteinLimit =
            if (goal.value != null && goal.value!!.startDate >= selectedDate.value || DateUtils.isToday(goal.value!!.startDate.time)) goal.value!!.proteinLimit else user.value!!.proteinLimit
        val proteinLimitLower =
            if (goal.value != null && goal.value!!.startDate >= selectedDate.value || DateUtils.isToday(goal.value!!.startDate.time)) goal.value!!.proteinLimitLower else user.value!!.proteinLimitLower
        val proteinLimitUpper =
            if (goal.value != null && goal.value!!.startDate >= selectedDate.value || DateUtils.isToday(goal.value!!.startDate.time)) goal.value!!.proteinLimitUpper else user.value!!.proteinLimitUpper

        val kcalEaten =
            selectedDateEntries.sumByDouble { entry -> entry.kcal }
        val carbsEaten =
            selectedDateEntries.sumByDouble { entry -> entry.carbs }
        val fatEaten =
            selectedDateEntries.sumByDouble { entry -> entry.fat }
        val proteinEaten =
            selectedDateEntries.sumByDouble { entry -> entry.protein }

        val kcalProgress = kcalEaten.toFloat() / calorieLimit * 100f
        val carbsProgress = carbsEaten.toFloat() / carbsLimit * 100f
        val fatProgress = fatEaten.toFloat() / fatLimit * 100f
        val proteinProgress = proteinEaten.toFloat() / proteinLimit * 100f

        val kcalLeft: Double =
            if (calorieLimit - kcalEaten < 0) 0.0 else calorieLimit - kcalEaten
        val carbsLeft: Double =
            if (carbsLimit - carbsEaten < 0) 0.0 else carbsLimit - carbsEaten
        val fatLeft: Double =
            if (fatLimit - fatEaten < 0) 0.0 else fatLimit - fatEaten
        val proteinLeft: Double =
            if (proteinLimit - proteinEaten < 0) 0.0 else proteinLimit - proteinEaten

        val kcalProgressColorId = when {
            kcalEaten < calorieLimitLower -> R.color.warning
            kcalEaten > calorieLimitUpper -> R.color.error
            else -> R.color.success
        }
        val carbsProgressColorId = when {
            carbsEaten < carbsLimitLower -> R.color.warning
            carbsEaten > carbsLimitUpper -> R.color.error
            else -> R.color.success
        }
        val fatProgressColorId = when {
            fatEaten < fatLimitLower -> R.color.warning
            fatEaten > fatLimitUpper -> R.color.error
            else -> R.color.success
        }
        val proteinProgressColorId = when {
            proteinEaten < proteinLimitLower -> R.color.warning
            proteinEaten > proteinLimitUpper -> R.color.error
            else -> R.color.success
        }


        return DaySummaryData(
            kcalEaten.toFloat(),
            calorieLimit,
            kcalLeft.toFloat(),
            kcalProgress,
            kcalProgressColorId,
            carbsEaten.toFloat(),
            carbsLimit,
            carbsLeft.toFloat(),
            carbsProgress.toInt(),
            carbsProgressColorId,
            fatEaten.toFloat(),
            fatLimit,
            fatLeft.toFloat(),
            fatProgress.toInt(),
            fatProgressColorId,
            proteinEaten.toFloat(),
            proteinLimit,
            proteinLeft.toFloat(),
            proteinProgress.toInt(),
            proteinProgressColorId
        )
    }

    fun fetchGoal() = viewModelScope.launch {
        try {
            goalRepository.getGoal()
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    data class DaySummaryData(
        val kcalEaten: Float,
        val kcalGoal: Float,
        val kcalLeft: Float,
        val kcalProgress: Float,
        val kcalProgressColorId: Int,

        val carbsEaten: Float,
        val carbsGoal: Float,
        val carbsLeft: Float,
        val carbsProgress: Int,
        val carbsProgressColorId: Int,

        val fatEaten: Float,
        val fatGoal: Float,
        val fatLeft: Float,
        val fatProgress: Int,
        val fatProgressColorId: Int,

        val proteinEaten: Float,
        val proteinGoal: Float,
        val proteinLeft: Float,
        val proteinProgress: Int,
        val proteinProgressColorId: Int

    )
}