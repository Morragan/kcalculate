package com.example.dietapp.ui.friends

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.models.MergedFriend
import com.example.dietapp.models.SearchUserDTO
import com.example.dietapp.ui.credits.CreditsActivity
import com.example.dietapp.ui.friends.fragments.FriendsFragment
import com.example.dietapp.ui.goals.GoalsActivity
import com.example.dietapp.ui.home.HomeActivity
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.ui.nointernet.NoInternetActivity
import com.example.dietapp.ui.profile.ProfileActivity
import com.example.dietapp.utils.DietDrawerBuilder
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import kotlinx.android.synthetic.main.activity_friends.*
import javax.inject.Inject

class FriendsActivity : AppCompatActivity(), FriendsView, FriendsAdapter.AcceptedOnClickListener,
    FriendsAdapter.PendingOnClickListener, FriendsAdapter.BlockedOnClickListener {

    @Inject
    lateinit var presenter: FriendsPresenter

    private lateinit var drawer: Drawer
    private lateinit var profileDrawerItem: ProfileDrawerItem
    private lateinit var accountHeader: AccountHeader

    private lateinit var viewPagerAdapter: FriendsPagerAdapter

    override fun showConnectionError() {
        startActivityForResult(Intent(this, NoInternetActivity::class.java), -1)
    }

    override fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun replaceFriends(friends: List<MergedFriend>) {
        val adapter = viewPagerAdapter.getItem(0).adapter
        adapter?.replaceUsers(friends)
        adapter?.filter?.filter("")
        findViewById<RecyclerView>(R.id.friends_recycler_view_friends).visibility =
            View.VISIBLE
        findViewById<ProgressBar>(R.id.friends_placeholder_friends).visibility = View.GONE

    }

    override fun replaceUsers(users: List<SearchUserDTO>) {

    }

    override fun showOperationResult(isSuccessful: Boolean) {

    }

    override fun onUnfriendClick(friendId: Int) {
        presenter.deleteFriend(friendId)
    }

    override fun onBlockClick(userId: Int) {
        presenter.blockUser(userId)
    }

    override fun onAcceptClick(friendId: Int) {
        presenter.acceptFriend(friendId)
    }

    override fun onRejectClick(friendId: Int) {
        presenter.deleteFriend(friendId)
    }

    override fun onUnblockClick(userId: Int) {
        presenter.deleteFriend(userId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        (application as DietApp).appComponent.newActivityComponent().inject(this)

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

        friends_input_search_people.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query.isNullOrBlank()) return true
                if (friends_view_pager.currentItem == 1) {
                    presenter.searchPeople(query)
                }
                // Hide keyboard
                val inputManager =
                    getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                val focusedView = currentFocus ?: View(this@FriendsActivity)
                inputManager.hideSoftInputFromWindow(focusedView.windowToken, 0)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(friends_view_pager.currentItem != 0 || newText == null) return false
                getCurrentFragment().adapter?.filter?.filter(newText)
                return false
            }

        })
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)

        viewPagerAdapter = FriendsPagerAdapter(supportFragmentManager, this)
        friends_view_pager.adapter = viewPagerAdapter
        friends_tabs.setupWithViewPager(friends_view_pager)

        presenter.loadFriends()
    }

    override fun onStop() {
        presenter.unbind()
        super.onStop()
    }

    private fun sync() {}
    private fun getCurrentFragment(): FriendsFragment {
        val position = friends_view_pager.currentItem
        return viewPagerAdapter.getItem(position)
    }
}
