package com.example.dietapp.utils

import android.app.Activity
import androidx.appcompat.widget.Toolbar
import com.example.dietapp.R
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem

object DietDrawerBuilder {
    @JvmStatic
    fun getBuilder(
        activity: Activity,
        toolbar: Toolbar,
        accountHeader: AccountHeader,
        onItemClickListener: Drawer.OnDrawerItemClickListener
    ): DrawerBuilder {

        val drawerHomeItem =
            PrimaryDrawerItem().withIdentifier(1).withName(R.string.nav_text_home)
                .withIcon(R.drawable.ic_home_black)
        val drawerFriendsItem =
            PrimaryDrawerItem().withIdentifier(2).withName(R.string.nav_text_friends)
                .withIcon(R.drawable.ic_people)
        val drawerGoalsItem =
            PrimaryDrawerItem().withIdentifier(3).withName(R.string.nav_text_goals)
                .withIcon(R.drawable.ic_goals_black)
        val drawerCreditsItem =
            SecondaryDrawerItem().withIdentifier(4).withName(R.string.nav_text_credits)
                .withIcon(R.drawable.ic_credits)
        val drawerSyncItem =
            SecondaryDrawerItem().withIdentifier(5).withName(R.string.nav_text_sync)
                .withIcon(R.drawable.ic_sync)
        val drawerLogoutItem =
            SecondaryDrawerItem().withIdentifier(100).withName(R.string.nav_text_logout)
                .withIcon(R.drawable.ic_logout)

        return DrawerBuilder().withActivity(activity).withToolbar(toolbar)
            .withAccountHeader(accountHeader).addDrawerItems(
                drawerHomeItem,
                drawerFriendsItem,
                drawerGoalsItem,
                DividerDrawerItem(),
                drawerCreditsItem,
                drawerSyncItem,
                drawerLogoutItem
            )
            .withOnDrawerItemClickListener(onItemClickListener)

    }
}