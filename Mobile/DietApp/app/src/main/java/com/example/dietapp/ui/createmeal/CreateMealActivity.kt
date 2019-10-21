package com.example.dietapp.ui.createmeal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.models.Nutrients
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.ui.nointernet.NoInternetActivity
import com.example.dietapp.utils.Converters
import kotlinx.android.synthetic.main.activity_create_meal.*
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class CreateMealActivity : AppCompatActivity(), CreateMealView {

    @Inject
    lateinit var presenter: CreateMealPresenter

    override fun showConnectionError() {
        startActivityForResult(Intent(this, NoInternetActivity::class.java), -1)
    }

    override fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun showError() {
        create_meal_button_create.doneLoadingAnimation(
            ContextCompat.getColor(this, R.color.error),
            Converters.drawableToBitmap(getDrawable(R.drawable.ic_error_white)!!)
        )

        Handler().postDelayed({
            login_button_login.revertAnimation()
        }, 1000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meal)

        (application as DietApp).appComponent.newActivityComponent().inject(this)

        create_meal_button_create.setOnClickListener {
            if (!validate()) return@setOnClickListener

            val name = create_meal_input_name.text.toString()
            val nutrients = Nutrients(
                create_meal_input_carbs.text.toString().toInt(),
                create_meal_input_fat.text.toString().toInt(),
                create_meal_input_protein.text.toString().toInt(),
                create_meal_input_kcal.text.toString().toInt()
            )
            presenter.addMeal(name, nutrients)

            create_meal_button_create.startAnimation()
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

    private fun validate(): Boolean {
        var valid = true

        val name = create_meal_input_name.text.toString()
        val kcal = create_meal_input_kcal.text.toString().toIntOrNull()
        val carbs = create_meal_input_carbs.text.toString().toIntOrNull()
        val fat = create_meal_input_carbs.text.toString().toIntOrNull()
        val protein = create_meal_input_carbs.text.toString().toIntOrNull()

        if (name.isBlank()) {
            create_meal_input_name.error = getString(R.string.error_meal_name_input)
            valid = false
        }

        if (kcal == null || kcal < 0) {
            create_meal_input_kcal.error = getString(R.string.error_meal_kcal_input)
            valid = false
        }
        if (carbs == null || carbs < 0 || carbs > 100) {
            create_meal_input_carbs.error = getString(R.string.error_meal_carbs_input)
            valid = false
        }
        if (fat == null || fat < 0 || fat > 100) {
            create_meal_input_fat.error = getString(R.string.error_meal_fat_input)
            valid = false
        }
        if (protein == null || protein < 0 || protein > 100) {
            create_meal_input_protein.error = getString(R.string.error_meal_protein_input)
            valid = false
        }

        return valid
    }
}
