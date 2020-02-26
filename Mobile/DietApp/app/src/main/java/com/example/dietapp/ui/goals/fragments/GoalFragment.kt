package com.example.dietapp.ui.goals.fragments


import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton

import com.example.dietapp.R
import com.example.dietapp.ui.goals.GoalsViewModel
import com.example.dietapp.ui.goals.InviteFriendsAdapter
import com.example.dietapp.utils.ButtonState
import com.example.dietapp.utils.Converters
import com.example.dietapp.utils.ViewState.*
import kotlinx.android.synthetic.main.fragment_goal.*
import kotlin.math.abs

/**
 * A simple [Fragment] subclass.
 */
class GoalFragment : BaseGoalFragment(), InviteFriendsAdapter.OnFriendCheckedListener {

    private val inviteFriendsDialogView by lazy {
        layoutInflater.inflate(R.layout.dialog_invite_friends, null)
    }
    private val inviteFriendsDialogNoFriends by lazy {
        inviteFriendsDialogView.findViewById<TextView>(
            R.id.dialog_invite_friends_no_friends
        )
    }
    private val inviteFriendsDialogRecyclerView by lazy {
        val recyclerView =
            inviteFriendsDialogView.findViewById<RecyclerView>(R.id.dialog_invite_friends_recycler_view)
        recyclerView.adapter = inviteFriendsAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        return@lazy recyclerView
    }

    private val inviteFriendsAdapter by lazy { InviteFriendsAdapter(this) }

    private val inviteFriendsDialog by lazy {
        AlertDialog.Builder(context!!)
            .setTitle(getString(R.string.text_invite_friends_dialog_title))
            .setView(inviteFriendsDialogView)
            .setPositiveButton(R.string.dialog_invite_friends_confirm) { _, _ -> }
            .create()
    }

    companion object {
        @JvmStatic
        fun newInstance(_viewModel: GoalsViewModel) =
            GoalFragment().apply {
                viewModel = _viewModel
            }
    }

    override fun onFriendChecked(friendId: Int) {
        viewModel!!.checkedFriendsIds.add(friendId)
    }

    override fun onFriendUncheckedListener(friendId: Int) {
        viewModel!!.checkedFriendsIds.remove(friendId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // region widgets event listeners setup
        goals_create_goal_number.apply {
            setFormatter {
                return@setFormatter (abs(it - 50) / 10f).toString()
            }
            setOnValueChangedListener { _, _, newVal ->
                viewModel!!.weightGoal.postValue(-(newVal - 50) / 10f)
                if (newVal - 50 == 0) goals_create_goal_number.textColor = R.color.error
                else goals_create_goal_number.textColor = R.color.textBlack
            }
        }

        goals_create_goal_button_invite.setOnClickListener {
            inviteFriendsDialog.show()
        }

        goals_invitation_button_accept.setOnClickListener {
            viewModel!!.acceptGoal()
        }

        goals_invitation_button_reject.setOnClickListener {
            viewModel!!.removeGoal()
        }

        goals_create_goal_button_create.setOnClickListener {
            if (viewModel!!.weightGoal.value == null || viewModel!!.weightGoal.value == 0f) {
                goals_create_goal_number.textColor = R.color.error
                return@setOnClickListener
            }

            viewModel!!.createGoal()
        }

        goals_goal_button_give_up.setOnClickListener {
            viewModel!!.removeGoal()
        }
        //endregion

        // region LiveData observers setup
        viewModel!!.goal.observe(viewLifecycleOwner, Observer {
            when {
                it == null -> viewModel!!.goalTabViewState.value = EMPTY
                it.status == 1 -> {
                    viewModel!!.goalTabViewState.value = DEFAULT
                }
                it.status == 2 -> {
                    viewModel!!.goalTabViewState.value = ITEMS
                }
            }
        })

        viewModel!!.invitationTextResource.observe(viewLifecycleOwner, Observer {
            goals_invitation_goal.text = getString(it, viewModel!!.goal.value?.weightGoal)
            goals_goal_text.text = getString(it, viewModel!!.goal.value?.weightGoal)
        })

        viewModel!!.friends.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                inviteFriendsDialogNoFriends.visibility = View.GONE
                inviteFriendsDialogRecyclerView.visibility = View.VISIBLE
                inviteFriendsAdapter.replaceFriends(it)
            } else {
                inviteFriendsDialogRecyclerView.visibility = View.GONE
                inviteFriendsDialogNoFriends.visibility = View.VISIBLE
            }
        })

        viewModel!!.createGoalTextResource.observe(viewLifecycleOwner, Observer {
            goals_create_goal_text.text = getString(it)
        })

        viewModel!!.goalTabViewState.observe(viewLifecycleOwner, Observer {
            @Suppress("NON_EXHAUSTIVE_WHEN", "WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (it) {
                LOADING -> showLoading()
                EMPTY -> showCreateGoal()
                ITEMS -> showGoal()
                DEFAULT -> showInvitation()
            }
        })

        viewModel!!.createGoalButtonState.observe(viewLifecycleOwner, Observer {
            @Suppress("NON_EXHAUSTIVE_WHEN")
            when (it) {
                ButtonState.LOADING -> goals_create_goal_button_create.startAnimation()
                ButtonState.FAIL -> showProgressButtonFailed(goals_create_goal_button_create)
                ButtonState.SUCCESS -> showProgressButtonSuccess(goals_create_goal_button_create)
            }
        })

        viewModel!!.giveUpButtonState.observe(viewLifecycleOwner, Observer {
            @Suppress("NON_EXHAUSTIVE_WHEN")
            when (it) {
                ButtonState.LOADING -> goals_goal_button_give_up.startAnimation()
                ButtonState.FAIL -> showProgressButtonFailed(goals_goal_button_give_up)
                ButtonState.SUCCESS -> showProgressButtonSuccess(goals_goal_button_give_up)
            }
        })

        viewModel!!.acceptButtonState.observe(viewLifecycleOwner, Observer {
            @Suppress("NON_EXHAUSTIVE_WHEN")
            when(it){
                ButtonState.LOADING -> goals_invitation_button_accept.startAnimation()
                ButtonState.FAIL -> showProgressButtonFailed(goals_invitation_button_accept)
                ButtonState.SUCCESS -> showProgressButtonSuccess(goals_invitation_button_accept)
            }
        })

        viewModel!!.rejectButtonState.observe(viewLifecycleOwner, Observer {
            @Suppress("NON_EXHAUSTIVE_WHEN")
            when(it){
                ButtonState.LOADING -> goals_invitation_button_reject.startAnimation()
                ButtonState.FAIL -> showProgressButtonFailed(goals_invitation_button_reject)
                ButtonState.SUCCESS -> showProgressButtonSuccess(goals_invitation_button_reject)
            }
        })
        // endregion
    }

    private fun showLoading() {
        goals_loading_bar.visibility = View.VISIBLE
        goals_goal_layout.visibility = View.GONE
        goals_create_goal_layout.visibility = View.GONE
        goals_invitation_layout.visibility = View.GONE
    }

    private fun showGoal() {
        goals_loading_bar.visibility = View.GONE
        goals_goal_layout.visibility = View.VISIBLE
        goals_create_goal_layout.visibility = View.GONE
        goals_invitation_layout.visibility = View.GONE
    }

    private fun showCreateGoal() {
        goals_loading_bar.visibility = View.GONE
        goals_goal_layout.visibility = View.GONE
        goals_create_goal_layout.visibility = View.VISIBLE
        goals_invitation_layout.visibility = View.GONE
    }

    private fun showInvitation() {
        goals_loading_bar.visibility = View.GONE
        goals_goal_layout.visibility = View.GONE
        goals_create_goal_layout.visibility = View.GONE
        goals_invitation_layout.visibility = View.VISIBLE
    }

    private fun showProgressButtonFailed(button: CircularProgressButton) {
        button.doneLoadingAnimation(
            ContextCompat.getColor(context!!, R.color.error),
            Converters.drawableToBitmap(activity!!.getDrawable(R.drawable.ic_error_white)!!)
        )
        Handler().postDelayed({
            button.revertAnimation()
        }, 600)
    }

    private fun showProgressButtonSuccess(button: CircularProgressButton) {
        button.doneLoadingAnimation(
            ContextCompat.getColor(context!!, R.color.success),
            Converters.drawableToBitmap(activity!!.getDrawable(R.drawable.ic_done_white)!!)
        )

        Handler().postDelayed({
            button.revertAnimation()
        }, 1000)
    }
}
