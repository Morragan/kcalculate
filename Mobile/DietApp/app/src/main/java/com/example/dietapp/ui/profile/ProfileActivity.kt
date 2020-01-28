package com.example.dietapp.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.ViewModelFactory
import com.example.dietapp.ui.calculatenutrientgoals.CalculateNutrientGoalsActivity
import com.example.dietapp.ui.calculatenutrientgoals.NutrientGoalsData
import com.example.dietapp.ui.credits.CreditsActivity
import com.example.dietapp.ui.friends.FriendsActivity
import com.example.dietapp.ui.goals.GoalsActivity
import com.example.dietapp.ui.home.HomeActivity
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.utils.Constants
import com.example.dietapp.utils.DietDrawerBuilder
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import kotlinx.android.synthetic.main.activity_profile.*
import pl.droidsonroids.gif.GifImageView
import javax.inject.Inject

class ProfileActivity : AppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ProfileViewModel
    private lateinit var drawer: Drawer
    private lateinit var profileDrawerItem: ProfileDrawerItem
    private lateinit var accountHeader: AccountHeader

    private val loadingDialog by lazy {
        val loadingGif = GifImageView(this).apply {
            setImageResource(R.mipmap.gif_loading)
        }
        AlertDialog.Builder(this)
            .setView(loadingGif)
            .setCancelable(false)
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setSupportActionBar(profile_toolbar)

        (application as DietApp).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)

        //region drawer setup
        profileDrawerItem = ProfileDrawerItem().withName("Name").withIdentifier(0)

        accountHeader = AccountHeaderBuilder()
            .withActivity(this)
            .addProfiles(profileDrawerItem)
            .build()

        val onDrawerItemClickListener = object : Drawer.OnDrawerItemClickListener {
            override fun onItemClick(
                view: View?,
                position: Int,
                drawerItem: IDrawerItem<*>
            ): Boolean {
                when (position) {
                    1 -> startActivity(Intent(this@ProfileActivity, HomeActivity::class.java))
                    2 -> startActivity(Intent(this@ProfileActivity, FriendsActivity::class.java))
                    3 -> startActivity(Intent(this@ProfileActivity, GoalsActivity::class.java))
                    5 -> startActivity(Intent(this@ProfileActivity, CreditsActivity::class.java))
                    6 -> sync()
                    7 -> startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
                }
                drawer.closeDrawer()
                return false
            }
        }

        drawer = DietDrawerBuilder.getBuilder(
            this,
            profile_toolbar,
            accountHeader,
            onDrawerItemClickListener
        ).build()
        // endregion

        profile_button_privacy.setOnClickListener {
            viewModel.isLoadingDialogVisible.value = true
            viewModel.toggleAccountIsPrivate().invokeOnCompletion {
                viewModel.isLoadingDialogVisible.postValue(false)
            }
        }

        profile_button_recalculate_limits.setOnClickListener {
            startActivityForResult(
                Intent(this, CalculateNutrientGoalsActivity::class.java),
                Constants.requestCodeCalculateGoals
            )
        }

        //region livedata observers setup
        viewModel.user.observe(this, Observer {
            profileDrawerItem = profileDrawerItem.withName(it.nickname).withEmail(it.email).apply {
                if(!it.avatarLink.isBlank()) withIcon(it.avatarLink)
            }
            accountHeader.updateProfile(profileDrawerItem)

            if(!it.avatarLink.isBlank())
                Glide.with(this).load(it.avatarLink).into(profile_avatar)
            profile_name.text = it.nickname
            profile_email.text = it.email
            profile_streak.text = "0"
            profile_points.text = it.points.toString()
            profile_icon_level.text = "2"
            profile_level_bar.progress = "30".toFloat()
            profile_level_points.text = getString(R.string.profile_text_level_points, 30, 200)
            profile_limits_lower.text = getString(
                R.string.profile_limits,
                it.calorieLimitLower,
                it.carbsLimitLower,
                it.fatLimitLower,
                it.proteinLimitLower
            )
            profile_limits_goal.text = getString(
                R.string.profile_limits,
                it.calorieLimit,
                it.carbsLimit,
                it.fatLimit,
                it.proteinLimit
            )
            profile_limits_upper.text = getString(
                R.string.profile_limits,
                it.calorieLimitUpper,
                it.carbsLimitUpper,
                it.fatLimitUpper,
                it.proteinLimitUpper
            )
            profile_label_privacy.text = if (it.isPrivate) HtmlCompat.fromHtml(
                getString(R.string.profile_private_description),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            ) else HtmlCompat.fromHtml(
                getString(R.string.profile_public_description),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            profile_button_privacy.text =
                if (it.isPrivate) getString(R.string.profile_button_text_make_public)
                else getString(R.string.profile_button_text_make_private)
        })

        viewModel.isLoadingDialogVisible.observe(this, Observer {
            if (it) loadingDialog.show()
            else loadingDialog.dismiss()
        })

        viewModel.loggedIn.observe(this, Observer { isLoggedIn ->
            if (!isLoggedIn) {
                val intent = Intent(this, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
            }
        })
        //endregion
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Constants.requestCodeCalculateGoals -> {
                if (resultCode == Activity.RESULT_OK) {
                    val nutrientGoals =
                        data!!.getParcelableExtra<NutrientGoalsData>(Constants.intentKeyCalculateNutrientGoalsResult)
                    viewModel.isLoadingDialogVisible.value = true
                    viewModel.updateNutrientGoals(nutrientGoals).invokeOnCompletion {
                        viewModel.isLoadingDialogVisible.value = false
                    }
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun sync() {

    }
}
