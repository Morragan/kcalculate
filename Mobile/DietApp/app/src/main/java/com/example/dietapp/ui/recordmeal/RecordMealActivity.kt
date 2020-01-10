package com.example.dietapp.ui.recordmeal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.models.dto.RecordMealDTO
import com.example.dietapp.ui.createmeal.CreateMealActivity
import com.example.dietapp.ui.recordmeal.fragments.RecordMealFragment
import com.example.dietapp.utils.Constants
import com.example.dietapp.utils.hideKeyboard
import com.example.dietapp.ViewModelFactory
import com.example.dietapp.ui.login.LoginActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_record_meal.*
import javax.inject.Inject

class RecordMealActivity : AppCompatActivity(), RecordMealAdapter.AddMealOnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: RecordMealViewModel
    lateinit var viewPagerAdapter: RecordMealPagerAdapter

    override fun onAddMealClick(meal: RecordMealDTO) {
        viewModel.recordMeal(meal)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_meal)

        (application as DietApp).appComponent.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(RecordMealViewModel::class.java)

        val createMealOnClickListener = View.OnClickListener {
            startActivityForResult(
                Intent(this, CreateMealActivity::class.java),
                Constants.requestCodeCreateMeal
            )
        }
        val scanBarcodeOnClickListener = View.OnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.setBeepEnabled(false)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
            scanner.initiateScan()
        }

        record_meal_input_search_food.setOnCloseListener {
            getCurrentFragment().onSearchBoxCancel()
            true
        }

        record_meal_input_search_food.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query == null) return true
                getCurrentFragment().onQuerySubmit(query)
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == null) return false
                getCurrentFragment().onQueryChange(newText)
                return false
            }
        })

        record_meal_icon_create_meal.setOnClickListener(createMealOnClickListener)
        record_meal_link_create_meal.setOnClickListener(createMealOnClickListener)
        record_meal_icon_scan_barcode.setOnClickListener(scanBarcodeOnClickListener)
        record_meal_link_scan_barcode.setOnClickListener(scanBarcodeOnClickListener)

        viewModel.fetchMeals()

        // region
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.requestCodeScanBarcode) {
            val result: IntentResult? =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents != null)
                    Toast.makeText(this, "Scanned " + result.contents, Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            }
        } else super.onActivityResult(requestCode, resultCode, data)


    }

    override fun onStart() {
        super.onStart()
        viewPagerAdapter = RecordMealPagerAdapter(supportFragmentManager, this, viewModel)
        record_meal_view_pager.adapter = viewPagerAdapter
        record_meal_tabs.setupWithViewPager(record_meal_view_pager)
    }

    private fun getCurrentFragment(): RecordMealFragment {
        val position = record_meal_view_pager.currentItem
        return viewPagerAdapter.getItem(position)
    }
}
