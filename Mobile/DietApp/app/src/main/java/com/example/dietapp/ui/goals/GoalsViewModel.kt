package com.example.dietapp.ui.goals

import androidx.lifecycle.*
import com.example.dietapp.R
import com.example.dietapp.api.exceptions.NotAuthorizedException
import com.example.dietapp.db.repositories.AccountRepository
import com.example.dietapp.db.repositories.FriendsRepository
import com.example.dietapp.db.repositories.GoalRepository
import com.example.dietapp.models.dto.CreateGoalDTO
import com.example.dietapp.models.entity.Friend
import com.example.dietapp.utils.ButtonState
import com.example.dietapp.utils.ViewState
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class GoalsViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val friendsRepository: FriendsRepository,
    private val goalRepository: GoalRepository
) : ViewModel() {
    val weightGoal = MutableLiveData<Float>(0f)
    val createGoalTextResource = Transformations.map(weightGoal) {
        if (it > 0) R.string.goals_create_goal_text_gain else R.string.goals_create_goal_text_lose
    }
    val checkedFriendsIds = mutableListOf<Int>()
    val friends = friendsRepository.allFriends
    val user = accountRepository.user
    val goal = goalRepository.goal

    val invitationTextResource = Transformations.map(goal) {
        if (it != null && it.weightGoal > 0) R.string.goals_goal_gain else R.string.goals_goal_lose
    }

    val participants = MediatorLiveData<List<Friend>>().apply {
        addSource(friends) { this.value = getParticipants() }
        addSource(user) { this.value = getParticipants() }
        addSource(goal) { this.value = getParticipants() }
    }
    val goalTabViewState = MutableLiveData<ViewState>()
    val rankingTabViewState = MutableLiveData<ViewState>()

    val createGoalButtonState = MutableLiveData<ButtonState>()
    val giveUpButtonState = MutableLiveData<ButtonState>()
    val acceptButtonState = MutableLiveData<ButtonState>()
    val rejectButtonState = MutableLiveData<ButtonState>()

    fun getGoal() = viewModelScope.launch {
        try {
            goalTabViewState.value = ViewState.LOADING
            goalRepository.getGoal()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun createGoal() = viewModelScope.launch {
        val goalDTO = CreateGoalDTO(weightGoal.value!!, checkedFriendsIds)
        try {
            createGoalButtonState.value = ButtonState.LOADING
            goalRepository.createGoal(goalDTO)
            createGoalButtonState.value = ButtonState.SUCCESS
        } catch (e: Exception) {
            createGoalButtonState.value = ButtonState.FAIL
            e.printStackTrace()
        }
    }

    fun acceptGoal() = viewModelScope.launch {
        try {
            acceptButtonState.value = ButtonState.LOADING
            goalRepository.acceptGoal()
            acceptButtonState.value = ButtonState.SUCCESS
        } catch (e: Exception) {
            acceptButtonState.value = ButtonState.FAIL
            e.printStackTrace()
        }
    }

    fun removeGoal() = viewModelScope.launch {
        try {
            giveUpButtonState.value = ButtonState.LOADING
            rejectButtonState.value = ButtonState.LOADING
            goalRepository.removeGoal()
            giveUpButtonState.value = ButtonState.SUCCESS
            rejectButtonState.value = ButtonState.SUCCESS
        } catch (e: Exception) {
            giveUpButtonState.value = ButtonState.FAIL
            rejectButtonState.value = ButtonState.FAIL
            e.printStackTrace()
        }
    }

    fun getFriends() = viewModelScope.launch {
        try {
            friendsRepository.fetchFriends()
        } catch (e: NotAuthorizedException) {
            accountRepository.logout()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getParticipants() =
        if (friends.value != null && user.value != null && goal.value != null)
            friends.value!!
                .filter { friend ->
                    friend.status == 2 && goal.value!!.participatingFriends.contains(
                        friend.id
                    )
                }
                .plus(user.value!!.toFriend())
                .sortedBy { friend -> friend.goalPoints }
        else
            null
}