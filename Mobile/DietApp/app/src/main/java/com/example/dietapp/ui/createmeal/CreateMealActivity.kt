package com.example.dietapp.ui.createmeal

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.ViewModelFactory
import com.example.dietapp.models.Nutrients
import com.example.dietapp.models.dto.CreateMealDTO
import com.example.dietapp.models.dto.CreatePublicMealDTO
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.utils.ButtonState.*
import com.example.dietapp.utils.Constants
import com.example.dietapp.utils.Converters
import kotlinx.android.synthetic.main.activity_create_meal.*
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class CreateMealActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: CreateMealViewModel
    var barcode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meal)

        (application as DietApp).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CreateMealViewModel::class.java)

        barcode = intent.getStringExtra(Constants.intentKeyRecordMealToCreateMealBarcode)
        if(!barcode.isNullOrEmpty()){
            create_meal_barcode.text = getString(R.string.create_meal_barcode, barcode)
            create_meal_barcode.visibility = View.VISIBLE
        }

        create_meal_button_create.setOnClickListener {
            if (!validate()) return@setOnClickListener

            val name = create_meal_input_name.text.toString()
            val nutrients = Nutrients(
                create_meal_input_carbs.text.toString().toDouble(),
                create_meal_input_fat.text.toString().toDouble(),
                create_meal_input_protein.text.toString().toDouble(),
                create_meal_input_kcal.text.toString().toDouble()
            )

            if(barcode.isNullOrEmpty()) viewModel.createMeal(CreateMealDTO(name, nutrients))
            else viewModel.createPublicMeal(CreatePublicMealDTO(name, nutrients, barcode!!))
        }

        // region LiveData observers setup
        viewModel.buttonState.observe(this, Observer {
            @Suppress("NON_EXHAUSTIVE_WHEN")
            when (it) {
                LOADING -> create_meal_button_create.startAnimation()
                FAIL -> showRecordFail()
                SUCCESS -> showRecordSuccess()
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
        viewModel.isSuccess.observe(this, Observer {
            if(it){
                viewModel.buttonState.value = SUCCESS
                finish()
            }
            else viewModel.buttonState.value = FAIL
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

    private fun showRecordSuccess() {
        create_meal_button_create.doneLoadingAnimation(
            ContextCompat.getColor(this, R.color.success),
            Converters.drawableToBitmap(getDrawable(R.drawable.ic_done_white)!!)
        )

        Handler().postDelayed({
            create_meal_button_create.revertAnimation()
        }, 1000)
    }

    private fun showRecordFail() {
        create_meal_button_create.doneLoadingAnimation(
            ContextCompat.getColor(this, R.color.error),
            Converters.drawableToBitmap(getDrawable(R.drawable.ic_error_white)!!)
        )

        Handler().postDelayed({
            login_button_login.revertAnimation()
        }, 1000)
    }
}
