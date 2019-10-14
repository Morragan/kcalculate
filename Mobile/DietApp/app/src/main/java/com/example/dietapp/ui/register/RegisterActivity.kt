package com.example.dietapp.ui.register

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.ui.register.fragments.RegisterBasicsFragment
import com.example.dietapp.ui.register.fragments.RegisterFatPercentageFragment
import com.example.dietapp.utils.Constants
import com.example.dietapp.utils.Converters
import com.example.dietapp.utils.Enums
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_register_result.*
import javax.inject.Inject

class RegisterActivity : AppCompatActivity(), RegisterView {
    @Inject
    lateinit var presenter: RegisterPresenter

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

    var calorieLimit: Int = 2000

    override fun showCalorieLimit(calorieLimit: Int) {
        register_view_pager.currentItem = register_view_pager.adapter!!.count - 1
        this.calorieLimit = calorieLimit
    }

    // TODO: handle circular button
    override fun showConnectionFailure() {
        register_text_connection_failure.visibility = View.VISIBLE
        register_button_register.doneLoadingAnimation(
            ContextCompat.getColor(this, R.color.error),
            Converters.drawableToBitmap(getDrawable(R.drawable.baseline_error_outline_black_48)!!)
        )
        Handler().postDelayed({
            register_button_register.revertAnimation()
        }, 1000)
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        register_text_connection_failure.visibility = View.GONE
        register_button_register.doneLoadingAnimation(
            ContextCompat.getColor(this, R.color.error),
            Converters.drawableToBitmap(getDrawable(R.drawable.baseline_error_outline_black_48)!!)
        )
        Handler().postDelayed({
            register_button_register.revertAnimation()
        }, 1000)
    }

    override fun startLoginActivity() {
        register_button_register.doneLoadingAnimation(
            ContextCompat.getColor(this, R.color.success),
            Converters.drawableToBitmap(getDrawable(R.drawable.baseline_done_black_48)!!)
        )

        Handler().postDelayed({
            register_button_register.revertAnimation()
        }, 1000)


        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra(Constants.intentKeyRegisterToLoginNickname, nickname)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val activityComponent = (application as DietApp).appComponent.newActivityComponent()
        activityComponent.inject(this)

        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.register_dialog_message)
            .setPositiveButton(R.string.register_dialog_positive_button)
            { _, _ -> }
            .setNegativeButton(R.string.register_dialog_negative_button)
            { _, _ ->
                register_view_pager.currentItem = register_view_pager.adapter!!.count - 1
            }

        register_view_pager.adapter = RegisterPagerAdapter(supportFragmentManager)

        register_button_proceed.setOnClickListener {
            val position = register_view_pager.currentItem
            val fragment = (register_view_pager.adapter as RegisterPagerAdapter).getItem(position)
            if (!fragment.validate()) return@setOnClickListener

            fragment.passData()

            if (fragment is RegisterBasicsFragment)
                builder.show()

            if (fragment is RegisterFatPercentageFragment) {
                presenter.calculateCalorieLimit(
                    heightCm,
                    weightKg,
                    gender,
                    weightGoal,
                    activityLevel,
                    fatPercentage
                )
                return@setOnClickListener
            }

            register_view_pager.currentItem++
        }

        register_button_register.setOnClickListener {
            register_button_register.startAnimation()
            presenter.onRegisterButtonClick(
                email,
                nickname,
                password,
                avatarLink,
                calorieLimit
            )
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)
    }

    override fun onStop() {
        presenter.unbind()
        super.onStop()
    }
}
