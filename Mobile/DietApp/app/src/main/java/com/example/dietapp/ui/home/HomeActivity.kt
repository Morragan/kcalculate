package com.example.dietapp.ui.home

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.ui.credits.CreditsActivity
import com.example.dietapp.ui.friends.FriendsActivity
import com.example.dietapp.ui.goals.GoalsActivity
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.ui.profile.ProfileActivity
import com.example.dietapp.ui.recordmeal.RecordMealActivity
import com.example.dietapp.utils.DietDrawerBuilder
import com.example.dietapp.utils.ProgressBarAnimator
import com.example.dietapp.ViewModelFactory
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject


class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: HomeViewModel
    private lateinit var drawer: Drawer
    private lateinit var profileDrawerItem: ProfileDrawerItem
    private lateinit var accountHeader: AccountHeader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(home_toolbar)

        (application as DietApp).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        //region drawer setup
        profileDrawerItem = ProfileDrawerItem().withName("Name").withIdentifier(0)

        accountHeader = AccountHeaderBuilder()
            .withActivity(this)
            .addProfiles(profileDrawerItem)
            .withOnAccountHeaderProfileImageListener(object :
                AccountHeader.OnAccountHeaderProfileImageListener {
                override fun onProfileImageClick(
                    view: View,
                    profile: IProfile<*>,
                    current: Boolean
                ): Boolean {
                    startActivity(Intent(this@HomeActivity, ProfileActivity::class.java))
                    return true
                }

                override fun onProfileImageLongClick(
                    view: View,
                    profile: IProfile<*>,
                    current: Boolean
                ): Boolean = false
            })
            .build()

        val onDrawerItemClickListener = object : Drawer.OnDrawerItemClickListener {
            override fun onItemClick(
                view: View?,
                position: Int,
                drawerItem: IDrawerItem<*>
            ): Boolean {
                when (position) {
                    2 -> startActivity(Intent(this@HomeActivity, FriendsActivity::class.java))
                    3 -> startActivity(Intent(this@HomeActivity, GoalsActivity::class.java))
                    5 -> startActivity(Intent(this@HomeActivity, CreditsActivity::class.java))
                    6 -> sync()
                    7 -> startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                }
                drawer.closeDrawer()
                return false
            }
        }

        drawer = DietDrawerBuilder.getBuilder(
            this,
            home_toolbar,
            accountHeader,
            onDrawerItemClickListener
        ).build()
        drawer.setSelection(1)
        // endregion

        home_add_fab.setOnClickListener {
            startActivity(Intent(this, RecordMealActivity::class.java))
        }

        home_button_day_back.setOnClickListener {
            viewModel.onDateButtonBackward()
        }

        home_button_day_forward.setOnClickListener {
            viewModel.onDateButtonForward()
        }

        home_app_bar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.app_bar_profile -> startActivity(Intent(this, ProfileActivity::class.java))
            }

            return@setOnMenuItemClickListener true
        }

        viewModel.fetchMealEntries()
        viewModel.fetchUserData()

        // region ViewModel observers setup
        viewModel.selectedDateString.observe(this, Observer {
            home_text_day.text = it
        })

        viewModel.selectedDateMealsSummary.observe(this, Observer {
            if (it == null) {
                return@Observer
            }
            // calories
            home_cpb.setProgressWithAnimation(it.kcalProgress, 800)
            home_text_goal.text = it.kcalGoal.toString()
            home_text_eaten.text = it.kcalEaten.toString()
            home_text_left.text = it.kcalLeft.toString()
            home_cpb.progressBarColor = getColor(it.kcalProgressColorId)
            home_text_left.setTextColor(it.kcalProgressColorId)

            // carbs
            home_progress_bar_carbs.isIndeterminate = false
            ProgressBarAnimator(home_progress_bar_carbs, 1200).setProgress(it.carbsProgress)
            home_text_carbs_goal.text = it.carbsGoal.toString()
            home_text_carbs_eaten.text = it.carbsEaten.toString()
            home_text_carbs_left.text = it.carbsLeft.toString()
            home_progress_bar_carbs.progressDrawable.setColorFilter(
                getColor(it.carbsProgressColorId),
                PorterDuff.Mode.SRC_IN
            )

            // fats
            home_progress_bar_fats.isIndeterminate = false
            ProgressBarAnimator(home_progress_bar_fats, 1200).setProgress(it.fatProgress)
            home_text_fats_goal.text = it.fatGoal.toString()
            home_text_fats_eaten.text = it.fatEaten.toString()
            home_text_fats_left.text = it.fatLeft.toString()
            home_progress_bar_fats.progressDrawable.setColorFilter(
                getColor(it.fatProgressColorId),
                PorterDuff.Mode.SRC_IN
            )

            // proteins
            home_progress_bar_proteins.isIndeterminate = false
            ProgressBarAnimator(home_progress_bar_proteins, 1200).setProgress(it.proteinProgress)
            home_text_proteins_goal.text = it.proteinGoal.toString()
            home_text_proteins_eaten.text = it.proteinEaten.toString()
            home_text_proteins_left.text = it.proteinLeft.toString()
            home_progress_bar_proteins.progressDrawable.setColorFilter(
                getColor(it.proteinProgressColorId),
                PorterDuff.Mode.SRC_IN
            )
        })

        viewModel.user.observe(this, Observer {
            profileDrawerItem = profileDrawerItem.withName(it.nickname).withEmail(it.email)
            accountHeader.updateProfile(profileDrawerItem)
        })

        viewModel.loggedIn.observe(this, Observer { isLoggedIn ->
            if (!isLoggedIn) {
                val intent = Intent(this, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
            }
        })
        // endregion
    }

    private fun sync() {}
}
