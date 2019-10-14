package com.example.dietapp.ui.register.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.dietapp.R
import com.example.dietapp.ui.login.LoginActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.fragment_register_method.*

class RegisterMethodFragment : RegisterFragment() {
    override fun passData() {}

    override fun validate() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_method, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFacebookLogin()

        val viewPager = activity!!.findViewById<ViewPager>(R.id.register_view_pager)
        register_button_email.setOnClickListener {
            viewPager.currentItem += 1
        }
        register_link_go_to_login.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        // Suppress proceed button, since fragment supplies it's own
        val proceedButton = activity!!.findViewById<Button>(R.id.register_button_proceed)
        proceedButton.visibility = View.GONE
    }

    override fun onPause() {
        // Make the button visible again
        val proceedButton = activity!!.findViewById<Button>(R.id.register_button_proceed)
        proceedButton.visibility = View.VISIBLE
        super.onPause()
    }

    private fun setupFacebookLogin() {
        val callbackManager = CallbackManager.Factory.create()
        register_button_facebook.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                Toast.makeText(context, "success", Toast.LENGTH_LONG).show()
            }

            override fun onCancel() {
                Toast.makeText(context, "cancel", Toast.LENGTH_LONG).show()
            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show()
            }

        })
    }
}