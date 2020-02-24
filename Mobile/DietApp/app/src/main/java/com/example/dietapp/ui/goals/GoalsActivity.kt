package com.example.dietapp.ui.goals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.ViewModelFactory
import com.example.dietapp.ui.credits.CreditsActivity
import com.example.dietapp.ui.friends.FriendsActivity
import com.example.dietapp.ui.home.HomeActivity
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.ui.profile.ProfileActivity
import com.example.dietapp.utils.DietDrawerBuilder
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import kotlinx.android.synthetic.main.activity_goals.*
import javax.inject.Inject

class GoalsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    private lateinit var drawer: Drawer
    private lateinit var profileDrawerItem: ProfileDrawerItem
    private lateinit var accountHeader: AccountHeader

    private lateinit var viewPagerAdapter: GoalsPagerAdapter

    private lateinit var viewModel: GoalsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)


        (application as DietApp).appComponent.inject(this)

        setSupportActionBar(goals_toolbar)

        //region drawer setup
        profileDrawerItem = ProfileDrawerItem()
            .withIdentifier(0)

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
                    startActivity(Intent(this@GoalsActivity, ProfileActivity::class.java))
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
                    1 -> startActivity(Intent(this@GoalsActivity, HomeActivity::class.java))
                    2 -> startActivity(Intent(this@GoalsActivity, FriendsActivity::class.java))
                    5 -> startActivity(Intent(this@GoalsActivity, CreditsActivity::class.java))
                    6 -> sync()
                    7 -> startActivity(Intent(this@GoalsActivity, LoginActivity::class.java))
                }
                drawer.closeDrawer()
                return false
            }
        }

        drawer = DietDrawerBuilder.getBuilder(
            this,
            goals_toolbar,
            accountHeader,
            onDrawerItemClickListener
        ).build()
        drawer.setSelection(3)
        //endregion

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(GoalsViewModel::class.java)

        viewPagerAdapter = GoalsPagerAdapter(supportFragmentManager, this, viewModel)
        goals_view_pager.adapter = viewPagerAdapter
        goals_tabs.setupWithViewPager(goals_view_pager)

        viewModel.user.observe(this, Observer {
            profileDrawerItem = profileDrawerItem.withName(it.nickname).withEmail(it.email).apply {
                if(!it.avatarLink.isBlank()) withIcon(it.avatarLink)
            }
            accountHeader.updateProfile(profileDrawerItem)
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.getGoal()
        viewModel.getFriends()
    }

    private fun sync() {
    }
}
