package com.example.dietapp.ui.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.ViewModelFactory
import com.example.dietapp.ui.calculatenutrientgoals.CalculateNutrientGoalsActivity
import com.example.dietapp.ui.calculatenutrientgoals.NutrientGoalsData
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.ui.register.fragments.RegisterBasicsFragment
import com.example.dietapp.ui.register.fragments.RegisterLoadingFragment
import com.example.dietapp.utils.Constants
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: RegisterViewModel

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra(Constants.intentKeyRegisterToLoginNickname, viewModel.nickname)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        (application as DietApp).appComponent.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)

        register_view_pager.adapter = RegisterPagerAdapter(supportFragmentManager, viewModel)

        register_button_proceed.setOnClickListener {
            val position = register_view_pager.currentItem
            val fragment = (register_view_pager.adapter as RegisterPagerAdapter).getItem(position)
            if (!fragment.validate(this)) return@setOnClickListener
            fragment.passData(this)
            register_view_pager.currentItem++

            if (fragment is RegisterBasicsFragment) {
                register_button_proceed.visibility = View.GONE
                startActivityForResult(
                    Intent(this, CalculateNutrientGoalsActivity::class.java),
                    Constants.requestCodeCalculateGoals
                )
            } else {
                register_button_proceed.visibility = View.VISIBLE
            }
        }

        // region LiveData observers setup

        viewModel.isRegistered.observe(this, Observer { isRegistered ->
            if (isRegistered) startLoginActivity()
        })

        // endregion
    }

    override fun onBackPressed() {
        if (register_view_pager.currentItem == 0) super.onBackPressed()
        else register_view_pager.currentItem--
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Constants.requestCodeCalculateGoals -> {
                if (resultCode == Activity.RESULT_OK) {
                    val nutrientGoals =
                        data!!.getParcelableExtra<NutrientGoalsData>(Constants.intentKeyCalculateNutrientGoalsResult)
                    viewModel.nutrientGoalsData = nutrientGoals
                    viewModel.register()
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
