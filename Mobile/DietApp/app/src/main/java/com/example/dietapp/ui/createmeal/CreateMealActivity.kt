package com.example.dietapp.ui.createmeal

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.models.Nutrients
import com.example.dietapp.models.dto.CreateMealDTO
import com.example.dietapp.utils.Converters
import com.example.dietapp.ViewModelFactory
import com.example.dietapp.ui.base.BaseActivity
import com.example.dietapp.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_create_meal.*
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class CreateMealActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: CreateMealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meal)

        (application as DietApp).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CreateMealViewModel::class.java)

        create_meal_button_create.setOnClickListener {
            if (!validate()) return@setOnClickListener

            val name = create_meal_input_name.text.toString()
            val nutrients = Nutrients(
                create_meal_input_carbs.text.toString().toInt(),
                create_meal_input_fat.text.toString().toInt(),
                create_meal_input_protein.text.toString().toInt(),
                create_meal_input_kcal.text.toString().toInt()
            )
            viewModel.createMeal(CreateMealDTO(name, nutrients))
        }

        // region
        viewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading) create_meal_button_create.startAnimation()
            else {
                create_meal_button_create.doneLoadingAnimation(
                    ContextCompat.getColor(this, R.color.error),
                    Converters.drawableToBitmap(getDrawable(R.drawable.ic_error_white)!!)
                )

                Handler().postDelayed({
                    login_button_login.revertAnimation()
                }, 1000)
            }
        })

        viewModel.loggedIn.observe(this, Observer { isLoggedIn ->
            if (!isLoggedIn) {
                val intent = Intent(this, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
            }
        })
        // endregion
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
