package com.example.dietapp.ui.recordmeal

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.models.MealDTO
import com.example.dietapp.models.RecordMealDTO
import com.example.dietapp.ui.createmeal.CreateMealActivity
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.ui.nointernet.NoInternetActivity
import com.example.dietapp.ui.recordmeal.fragments.RecordMealFragment
import com.example.dietapp.utils.Constants
import com.example.dietapp.utils.Converters
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_record_meal.*
import javax.inject.Inject

class RecordMealActivity : AppCompatActivity(), RecordMealView,
    RecordMealAdapter.AddMealOnClickListener {

    @Inject
    lateinit var presenter: RecordMealPresenter

    lateinit var viewPagerAdapter: RecordMealPagerAdapter

    override fun showConnectionError() {
        startActivityForResult(Intent(this, NoInternetActivity::class.java), -1)
    }

    override fun replaceUserMeals(meals: List<MealDTO>) {
        viewPagerAdapter.getItem(0).adapter?.replaceMeals(meals)
        findViewById<RecyclerView>(R.id.record_meal_recycler_view_my_meals).visibility =
            View.VISIBLE
        findViewById<TextView>(R.id.record_meal_placeholer_my_meals).visibility = View.GONE

    }

    override fun onAddMealClick(meal: RecordMealDTO) {
        presenter.recordMeal(meal)
    }

    override fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun stopCpbAnimation(isSuccess: Boolean) {
        val button =
            findViewById<CircularProgressButton?>(R.id.record_meal_list_item_button_add_meal)
        if (isSuccess) {
            button?.doneLoadingAnimation(
                ContextCompat.getColor(this, R.color.success),
                Converters.drawableToBitmap(getDrawable(R.drawable.baseline_done_black_48)!!)
            )
            Handler().postDelayed({
                button?.revertAnimation()
            }, 1200)
        } else {
            button?.doneLoadingAnimation(
                ContextCompat.getColor(this, R.color.error),
                Converters.drawableToBitmap(getDrawable(R.drawable.baseline_error_outline_black_48)!!)
            )
            Handler().postDelayed({
                button?.revertAnimation()
            }, 1200)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_meal)

        (application as DietApp).appComponent.newActivityComponent().inject(this)

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

        record_meal_input_search_food.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (record_meal_view_pager.currentItem != 1 || query.isNullOrBlank()) return true

                presenter.searchMeals(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (record_meal_view_pager.currentItem != 0 || newText.isNullOrBlank()) return false
                getCurrentFragment().adapter?.filter?.filter(newText)
                return false
            }

        })

        record_meal_icon_create_meal.setOnClickListener(createMealOnClickListener)
        record_meal_link_create_meal.setOnClickListener(createMealOnClickListener)
        record_meal_icon_scan_barcode.setOnClickListener(scanBarcodeOnClickListener)
        record_meal_link_scan_barcode.setOnClickListener(scanBarcodeOnClickListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //TODO: sprawdzić jaki request code ma scanner
        val result: IntentResult? =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        // reqCode = 49374
        if (result != null) {
            if (result.contents != null)
                Toast.makeText(this, "Scanned " + result.contents, Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
        } else
            super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)

        viewPagerAdapter = RecordMealPagerAdapter(supportFragmentManager, this)
        record_meal_view_pager.adapter = viewPagerAdapter
        record_meal_tabs.setupWithViewPager(record_meal_view_pager)

        presenter.getMeals()
    }

    override fun onStop() {
        presenter.unbind()
        super.onStop()
    }

    private fun getCurrentFragment(): RecordMealFragment {
        val position = record_meal_view_pager.currentItem
        return (record_meal_view_pager.adapter as RecordMealPagerAdapter).getItem(position)
    }
}
