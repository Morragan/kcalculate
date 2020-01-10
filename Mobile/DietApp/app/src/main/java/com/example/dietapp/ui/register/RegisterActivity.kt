package com.example.dietapp.ui.register

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.ui.register.fragments.RegisterBasicsFragment
import com.example.dietapp.ui.register.fragments.RegisterQuizFragment
import com.example.dietapp.utils.Constants
import com.example.dietapp.utils.Converters
import com.example.dietapp.utils.Enums
import com.example.dietapp.ViewModelFactory
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: RegisterViewModel

    lateinit var email: String
    lateinit var nickname: String
    lateinit var password: String
    lateinit var avatarLink: String

    var heightCm: Float = 0F
    var weightKg: Float = 0F
    lateinit var gender: Enums.Gender

    lateinit var weightGoal: Enums.WeightGoal
    lateinit var activityLevel: Enums.ActivityLevel

    lateinit var fatPercentage: Enums.BodyFatPercentage

    var calorieLimit: Int = 2500
    var carbsLimit: Int = 0
    var fatLimit: Int = 0
    var proteinLimit: Int = 0

//    override fun showConnectionFailure() {
//        register_text_connection_failure.visibility = View.VISIBLE
//        register_button_register.doneLoadingAnimation(
//            ContextCompat.getColor(this, R.color.error),
//            Converters.drawableToBitmap(getDrawable(R.drawable.ic_error_white)!!)
//        )
//        Handler().postDelayed({
//            register_button_register.revertAnimation()
//        }, 1000)
//    }

//    override fun showErrorMessage(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
//        register_text_connection_failure.visibility = View.GONE
//        register_button_register.doneLoadingAnimation(
//            ContextCompat.getColor(this, R.color.error),
//            Converters.drawableToBitmap(getDrawable(R.drawable.ic_error_white)!!)
//        )
//        Handler().postDelayed({
//            register_button_register.revertAnimation()
//        }, 1000)
//    }

    private fun startLoginActivity() {
        register_button_register.doneLoadingAnimation(
            ContextCompat.getColor(this, R.color.success),
            Converters.drawableToBitmap(getDrawable(R.drawable.ic_done_white)!!)
        )

        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra(Constants.intentKeyRegisterToLoginNickname, nickname)

        Handler().postDelayed({
            register_button_register.revertAnimation()
            startActivity(intent)
        }, 1000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        (application as DietApp).appComponent.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)

        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.register_dialog_message)
            .setPositiveButton(R.string.register_dialog_positive_button)
            { _, _ -> }
            .setNegativeButton(R.string.register_dialog_negative_button)
            { _, _ ->
                register_view_pager.currentItem = register_view_pager.adapter!!.count - 1
            }

        register_view_pager.adapter = RegisterPagerAdapter(supportFragmentManager, viewModel)

        register_button_proceed.setOnClickListener {
            val position = register_view_pager.currentItem
            val fragment = (register_view_pager.adapter as RegisterPagerAdapter).getItem(position)
            if (!fragment.validate(this)) return@setOnClickListener

            fragment.passData(this)

            if (fragment is RegisterBasicsFragment)
                builder.show()

            if (fragment is RegisterQuizFragment) {
                viewModel.calculateCalorieLimit(
                    heightCm,
                    weightKg,
                    gender,
                    weightGoal,
                    activityLevel
                )
                return@setOnClickListener
            }

            register_view_pager.currentItem++
        }

        register_button_register.setOnClickListener {
            register_button_register.startAnimation()
            viewModel.register(
                email,
                nickname,
                password,
                avatarLink,
                calorieLimit
            )
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
}
