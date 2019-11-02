package com.example.dietapp.ui.friends

import android.content.SharedPreferences
import com.example.dietapp.api.AccountService
import com.example.dietapp.api.SocialService
import com.example.dietapp.di.scopes.ActivityScope
import com.example.dietapp.models.FriendsDTO
import com.example.dietapp.models.MergedFriend
import com.example.dietapp.models.SearchUserDTO
import com.example.dietapp.models.TokenDTO
import com.example.dietapp.ui.base.BasePresenter
import com.example.dietapp.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@ActivityScope
class FriendsPresenter @Inject constructor(
    private val socialService: SocialService,
    private val accountService: AccountService,
    private val sharedPreferences: SharedPreferences
) : BasePresenter<FriendsView>() {

    fun loadFriends() {
        socialService.getFriends().enqueue(object : Callback<FriendsDTO> {
            override fun onFailure(call: Call<FriendsDTO>, t: Throwable) {
                mvpView?.showConnectionError()
            }

            override fun onResponse(
                call: Call<FriendsDTO>,
                response: Response<FriendsDTO>
            ) {
                if (!response.isSuccessful) {
                    if (response.code() == 401) tryRefreshToken()
                    return
                }
                val friends = response.body()!!
                val mergedFriends: MutableList<MergedFriend> = mutableListOf()

                val req = friends.requestedFriends.map { friend ->
                    MergedFriend(
                        friend.id,
                        friend.nickname,
                        friend.avatarLink,
                        friend.points,
                        friend.status,
                        true
                    )
                }

                val rec = friends.receivedFriends.map { friend ->
                    MergedFriend(
                        friend.id,
                        friend.nickname,
                        friend.avatarLink,
                        friend.points,
                        friend.status,
                        false
                    )
                }

                mergedFriends.addAll(req)
                mergedFriends.addAll(rec)

                mergedFriends.sortBy { friend -> friend.status }

                mvpView?.replaceFriends(mergedFriends)
            }
        })
    }

    fun requestFriend(friendID: Int) {
        socialService.requestFriend(friendID).enqueue(object : Callback<FriendsDTO> {
            override fun onFailure(call: Call<FriendsDTO>, t: Throwable) {
                mvpView?.showConnectionError()
            }

            override fun onResponse(call: Call<FriendsDTO>, response: Response<FriendsDTO>) {
                if (!response.isSuccessful) {
                    if (response.code() == 401) tryRefreshToken()
                    return
                }
            }
        })
    }

    fun deleteFriend(friendID: Int){
        socialService.deleteFriend(friendID).enqueue(object: Callback<FriendsDTO>{
            override fun onFailure(call: Call<FriendsDTO>, t: Throwable) {
                mvpView?.showConnectionError()
            }

            override fun onResponse(call: Call<FriendsDTO>, response: Response<FriendsDTO>) {
                if (!response.isSuccessful) {
                    if (response.code() == 401) tryRefreshToken()
                    return
                }
            }

        })
    }

    fun acceptFriend(friendID: Int) {
        socialService.acceptFriend(friendID).enqueue(object : Callback<FriendsDTO> {
            override fun onFailure(call: Call<FriendsDTO>, t: Throwable) {
                mvpView?.showConnectionError()
            }

            override fun onResponse(call: Call<FriendsDTO>, response: Response<FriendsDTO>) {
                if (!response.isSuccessful) {
                    if (response.code() == 401) tryRefreshToken()
                    return
                }
            }
        })
    }

    fun blockUser(userID: Int) {
        socialService.blockUser(userID).enqueue(object : Callback<FriendsDTO> {
            override fun onFailure(call: Call<FriendsDTO>, t: Throwable) {
                mvpView?.showConnectionError()
            }

            override fun onResponse(call: Call<FriendsDTO>, response: Response<FriendsDTO>) {
                if (!response.isSuccessful) {
                    if (response.code() == 401) tryRefreshToken()
                    return
                }

            }
        })
    }

    fun searchPeople(nickname: String) {
        socialService.searchPeople(nickname).enqueue(object : Callback<List<SearchUserDTO>> {
            override fun onFailure(call: Call<List<SearchUserDTO>>, t: Throwable) {
                mvpView?.showConnectionError()
            }

            override fun onResponse(
                call: Call<List<SearchUserDTO>>,
                response: Response<List<SearchUserDTO>>
            ) {
                if (!response.isSuccessful) {
                    if (response.code() == 401) tryRefreshToken()
                    return
                }
                mvpView?.replaceUsers(response.body()!!)
            }
        })
    }

    private fun tryRefreshToken() {
        val accessToken =
            sharedPreferences.getString(Constants.sharedPreferencesKeyAccessToken, "")!!
        val refreshToken =
            sharedPreferences.getString(Constants.sharedPreferencesKeyRefreshToken, "")!!

        accountService.refreshToken(accessToken, refreshToken).enqueue(object : Callback<TokenDTO> {
            override fun onFailure(call: Call<TokenDTO>, t: Throwable) {
                with(sharedPreferences.edit()) {
                    remove(Constants.sharedPreferencesKeyAccessToken)
                    remove(Constants.sharedPreferencesKeyRefreshToken)
                    remove(Constants.sharedPreferencesKeyTokenExpiration)
                    apply()
                }
                mvpView?.logout()
            }

            override fun onResponse(call: Call<TokenDTO>, response: Response<TokenDTO>) {
                if (!response.isSuccessful) {
                    with(sharedPreferences.edit()) {
                        remove(Constants.sharedPreferencesKeyAccessToken)
                        remove(Constants.sharedPreferencesKeyRefreshToken)
                        remove(Constants.sharedPreferencesKeyTokenExpiration)
                        apply()
                    }
                    mvpView?.logout()
                    return
                }
                val token = response.body()!!
                with(sharedPreferences.edit()) {
                    putString(Constants.sharedPreferencesKeyAccessToken, token.accessToken)
                    putString(Constants.sharedPreferencesKeyRefreshToken, token.refreshToken)
                    putLong(Constants.sharedPreferencesKeyTokenExpiration, token.expiration)
                    commit()
                }
                //TODO: try call again / prompt user to try again
            }
        })
    }
}