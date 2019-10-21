package com.example.dietapp.ui.home

import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.dietapp.DietApp
import com.example.dietapp.utils.DietDrawerBuilder
import com.example.dietapp.R
import com.example.dietapp.ui.credits.CreditsActivity
import com.example.dietapp.ui.friends.FriendsActivity
import com.example.dietapp.ui.goals.GoalsActivity
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.ui.nointernet.NoInternetActivity
import com.example.dietapp.ui.profile.ProfileActivity
import com.example.dietapp.ui.recordmeal.RecordMealActivity
import com.example.dietapp.utils.Methods
import com.example.dietapp.utils.ProgressBarAnimator
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import kotlinx.android.synthetic.main.activity_home.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), HomeView {

    @Inject
    lateinit var presenter: HomePresenter

    private lateinit var drawer: Drawer
    private lateinit var profileDrawerItem: ProfileDrawerItem
    private lateinit var accountHeader: AccountHeader

    override fun showConnectionError() {
        startActivityForResult(Intent(this, NoInternetActivity::class.java), -1)
    }

    override fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun changeDay(date: Date) {
        when {
            Methods.datesAreTheSameDay(date, Date()) -> home_text_day.setText(R.string.today)
            Methods.datesAreTheSameDay(
                date,
                Methods.offsetDate(Date(), -1)
            ) -> home_text_day.setText(
                R.string.yesterday
            )
            else -> {
                val formatter = SimpleDateFormat("EEE, d MMM yy", Locale.getDefault())
                home_text_day.text = formatter.format(date)
            }
        }
    }

    override fun updateProfile(name: String, email: String, avatarLink: Uri) {
        profileDrawerItem = profileDrawerItem.withName(name).withEmail(email)
        accountHeader.updateProfile(profileDrawerItem)
    }

    override fun updateKcalProgress(
        progress: Float,
        goal: Int,
        eaten: Int,
        left: Int,
        progressBarColorId: Int
    ) {
        runOnUiThread {
            home_cpb.setProgressWithAnimation(progress, 800)
            home_text_goal.text = goal.toString()
            home_text_eaten.text = eaten.toString()
            home_text_left.text = left.toString()
            home_cpb.progressBarColor = getColor(progressBarColorId)
        }
    }

    override fun updateCarbsProgress(
        progress: Int,
        goal: Int,
        eaten: Int,
        left: Int,
        progressBarColorId: Int
    ) {
        runOnUiThread {
            home_progress_bar_carbs.isIndeterminate = false
            ProgressBarAnimator(home_progress_bar_carbs, 1200).setProgress(progress)
            home_text_carbs_goal.text = goal.toString()
            home_text_carbs_eaten.text = eaten.toString()
            home_text_carbs_left.text = left.toString()
            home_progress_bar_carbs.progressDrawable.setColorFilter(
                getColor(progressBarColorId),
                PorterDuff.Mode.SRC_IN
            )
        }
    }

    override fun updateFatProgress(
        progress: Int,
        goal: Int,
        eaten: Int,
        left: Int,
        progressBarColorId: Int
    ) {
        runOnUiThread {
            home_progress_bar_fats.isIndeterminate = false
            ProgressBarAnimator(home_progress_bar_fats, 1200).setProgress(progress)
            home_text_fats_goal.text = goal.toString()
            home_text_fats_eaten.text = eaten.toString()
            home_text_fats_left.text = left.toString()
            home_progress_bar_fats.progressDrawable.setColorFilter(
                getColor(progressBarColorId),
                PorterDuff.Mode.SRC_IN
            )
        }
    }

    override fun updateProteinProgress(
        progress: Int,
        goal: Int,
        eaten: Int,
        left: Int,
        progressBarColorId: Int
    ) {
        runOnUiThread {
            home_progress_bar_proteins.isIndeterminate = false
            ProgressBarAnimator(home_progress_bar_proteins, 1200).setProgress(progress)
            home_text_proteins_goal.text = goal.toString()
            home_text_proteins_eaten.text = eaten.toString()
            home_text_proteins_left.text = left.toString()
            home_progress_bar_proteins.progressDrawable.setColorFilter(
                getColor(progressBarColorId),
                PorterDuff.Mode.SRC_IN
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(home_toolbar)

        val activityComponent = (application as DietApp).appComponent.newActivityComponent()
        activityComponent.inject(this)

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
                when(position){
                    2 -> startActivity(Intent(this@HomeActivity, FriendsActivity::class.java))
                    3 -> startActivity(Intent(this@HomeActivity, GoalsActivity::class.java))
                    5 -> startActivity(Intent(this@HomeActivity, CreditsActivity::class.java))
                    6 -> sync()
                    7 -> startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                }
                return true
            }
        }

        drawer = DietDrawerBuilder.getBuilder(
            this,
            home_toolbar,
            accountHeader,
            onDrawerItemClickListener
        ).build()


        home_add_fab.setOnClickListener {
            startActivity(Intent(this, RecordMealActivity::class.java))
        }

        home_button_day_back.setOnClickListener {
            presenter.onDateButtonBackward()
        }

        home_button_day_forward.setOnClickListener {
            presenter.onDateButtonForward()
        }

        home_app_bar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.app_bar_profile -> startActivity(Intent(this, ProfileActivity::class.java))
            }

            return@setOnMenuItemClickListener true
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)

        presenter.loadData()
    }

    override fun onStop() {
        presenter.unbind()
        super.onStop()
    }

    private fun sync(){}
}
