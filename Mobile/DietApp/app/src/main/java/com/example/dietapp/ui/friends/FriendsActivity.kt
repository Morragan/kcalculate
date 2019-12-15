package com.example.dietapp.ui.friends

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.ui.credits.CreditsActivity
import com.example.dietapp.ui.friends.fragments.FriendsFragment
import com.example.dietapp.ui.goals.GoalsActivity
import com.example.dietapp.ui.home.HomeActivity
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.ui.profile.ProfileActivity
import com.example.dietapp.utils.DietDrawerBuilder
import com.example.dietapp.viewmodels.ViewModelFactory
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import kotlinx.android.synthetic.main.activity_friends.*
import javax.inject.Inject

class FriendsActivity : AppCompatActivity(), FriendsAdapter.AcceptedOnClickListener,
    FriendsAdapter.PendingOnClickListener, FriendsAdapter.BlockedOnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var drawer: Drawer
    private lateinit var profileDrawerItem: ProfileDrawerItem
    private lateinit var accountHeader: AccountHeader

    private lateinit var viewPagerAdapter: FriendsPagerAdapter

    private lateinit var viewModel: FriendsViewModel

    override fun onUnfriendClick(friendId: Int) {
        viewModel.deleteUser(friendId)
    }

    override fun onBlockClick(userId: Int) {
        viewModel.blockUser(userId)
    }

    override fun onAcceptClick(friendId: Int) {
        viewModel.acceptFriendRequest(friendId)
    }

    override fun onRejectClick(friendId: Int) {
        viewModel.deleteUser(friendId)
    }

    override fun onUnblockClick(userId: Int) {
        viewModel.deleteUser(userId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        (application as DietApp).appComponent.inject(this)

        setSupportActionBar(friends_toolbar)

        profileDrawerItem = ProfileDrawerItem().withIdentifier(0).withName(DietApp.user?.nickname)
            .withEmail(DietApp.user?.email)

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
                    startActivity(Intent(this@FriendsActivity, ProfileActivity::class.java))
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
                    1 -> startActivity(Intent(this@FriendsActivity, HomeActivity::class.java))
                    3 -> startActivity(Intent(this@FriendsActivity, GoalsActivity::class.java))
                    5 -> startActivity(Intent(this@FriendsActivity, CreditsActivity::class.java))
                    6 -> sync()
                    7 -> startActivity(Intent(this@FriendsActivity, LoginActivity::class.java))
                }
                drawer.closeDrawer()
                return false
            }
        }

        drawer = DietDrawerBuilder.getBuilder(
            this,
            friends_toolbar,
            accountHeader,
            onDrawerItemClickListener
        ).build()
        drawer.setSelection(2)

        friends_input_search_people.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrBlank()) return true
                if (friends_view_pager.currentItem == 1) {
                    viewModel.searchPeople(query)
                }
                // Hide keyboard
                val inputManager =
                    getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                val focusedView = currentFocus ?: View(this@FriendsActivity)
                inputManager.hideSoftInputFromWindow(focusedView.windowToken, 0)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (friends_view_pager.currentItem != 0 || newText == null) return false
                getCurrentFragment().adapter?.filter?.filter(newText)
                return false
            }
        })

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(FriendsViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        viewPagerAdapter = FriendsPagerAdapter(supportFragmentManager, this)
        friends_view_pager.adapter = viewPagerAdapter
        friends_tabs.setupWithViewPager(friends_view_pager)

        viewModel.allFriends.observe(this, Observer {
            getCurrentFragment().adapter?.replaceUsers(it)
        })

        viewModel.fetchFriends()
    }

    private fun sync() {}

    private fun getCurrentFragment(): FriendsFragment {
        val position = friends_view_pager.currentItem
        return viewPagerAdapter.getItem(position)
    }
}
