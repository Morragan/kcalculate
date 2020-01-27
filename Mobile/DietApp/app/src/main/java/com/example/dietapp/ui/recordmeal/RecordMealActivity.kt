package com.example.dietapp.ui.recordmeal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.ViewModelFactory
import com.example.dietapp.models.dto.RecordMealDTO
import com.example.dietapp.ui.createmeal.CreateMealActivity
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.ui.recordmeal.fragments.RecordMealFragment
import com.example.dietapp.utils.ViewState
import com.example.dietapp.utils.Constants
import com.example.dietapp.utils.hideKeyboard
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_record_meal.*
import pl.droidsonroids.gif.GifImageView
import javax.inject.Inject

class RecordMealActivity : AppCompatActivity(), RecordMealAdapter.AddMealOnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: RecordMealViewModel
    lateinit var viewPagerAdapter: RecordMealPagerAdapter
    lateinit var barcode: String

    //region Dialogs setup
    private val loadingDialog by lazy {
        val loadingGif = GifImageView(this).apply {
            setImageResource(R.mipmap.gif_loading)
        }
        AlertDialog.Builder(this)
            .setView(loadingGif)
            .setCancelable(false)
            .create()
    }
    private val barcodeFoundDialogView by lazy {
        layoutInflater.inflate(
            R.layout.dialog_barcode,
            null
        )
    }
    private val dialogMealName by lazy { barcodeFoundDialogView.findViewById<TextView>(R.id.record_meal_dialog_title) }
    private val dialogKcal by lazy { barcodeFoundDialogView.findViewById<TextView>(R.id.record_meal_dialog_kcalper100) }
    private val dialogCarbs by lazy { barcodeFoundDialogView.findViewById<TextView>(R.id.record_meal_dialog_carbs) }
    private val dialogFat by lazy { barcodeFoundDialogView.findViewById<TextView>(R.id.record_meal_dialog_fats) }
    private val dialogProtein by lazy { barcodeFoundDialogView.findViewById<TextView>(R.id.record_meal_dialog_protein) }
    private val barcodeFoundDialog by lazy {
        AlertDialog.Builder(this)
            .setView(barcodeFoundDialogView)
            .setTitle(R.string.text_barcode_found_dialog_title)
            .setPositiveButton(R.string.record) { _, _ ->
                val meal = viewModel.foundBarcodeMeal.value!!.toCreateMealDTO()
                viewModel.createMeal(meal)
                viewModel.recordMeal(RecordMealDTO(meal.name, meal.nutrients, 0))
            }
            .setNegativeButton(R.string.button_text_create) { _, _ ->
                val meal = viewModel.foundBarcodeMeal.value!!.toCreateMealDTO()
                viewModel.createMeal(meal)
            }
            .setNegativeButton(R.string.button_text_cancel) { _, _ -> }
            .create()
    }
    private val barcodeNotFoundDialog by lazy {
        AlertDialog.Builder(this)
            .setTitle(R.string.text_barcode_not_found_dialog_title)
            .setMessage(R.string.text_barcode_not_found_message)
            .setPositiveButton(R.string.button_text_create) { _, _ ->
                startActivity(Intent(this, CreateMealActivity::class.java)
                    .apply {
                        putExtra(
                            Constants.intentKeyRecordMealToCreateMealBarcode,
                            barcode
                        )
                    })
            }
            .setNegativeButton(R.string.button_text_cancel) { _, _ -> }
            .create()
    }
    //endregion

    override fun onAddMealClick(meal: RecordMealDTO) {
        viewModel.isLoadingDialogVisible.value = true
        val job = viewModel.recordMeal(meal)
        job.invokeOnCompletion {
            viewModel.isLoadingDialogVisible.value = false
            Toast.makeText(this, R.string.meal_entry_success, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_meal)

        (application as DietApp).appComponent.inject(this)

        setSupportActionBar(record_meal_toolbar)

        viewModel = ViewModelProvider(this, viewModelFactory).get(RecordMealViewModel::class.java)

        viewModel.userMealsFragmentViewState.value = ViewState.LOADING
        viewModel.fetchMeals()

        //region controls listeners setup
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

        record_meal_icon_create_meal.setOnClickListener(createMealOnClickListener)
        record_meal_link_create_meal.setOnClickListener(createMealOnClickListener)
        record_meal_icon_scan_barcode.setOnClickListener(scanBarcodeOnClickListener)
        record_meal_link_scan_barcode.setOnClickListener(scanBarcodeOnClickListener)
        //endregion

        // region LiveData observers setup
        viewModel.loggedIn.observe(this, Observer { isLoggedIn ->

            if (!isLoggedIn) {
                val intent = Intent(this, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
            }
        })

        viewModel.isLoadingDialogVisible.observe(this, Observer {
            if (it) loadingDialog.show()
            else loadingDialog.dismiss()
        })

        viewModel.isBarcodeFoundDialogVisible.observe(this, Observer {
            if (it) barcodeFoundDialog.show()
            else barcodeFoundDialog.dismiss()
        })

        viewModel.isBarcodeNotFoundDialogVisible.observe(this, Observer {
            if (it) barcodeNotFoundDialog.show()
            else barcodeNotFoundDialog.dismiss()
        })

        viewModel.foundBarcodeMeal.observe(this, Observer {
            if (it != null) {
                dialogMealName.text = it.name
                dialogKcal.text = it.nutrients.kcal.toString()
                dialogCarbs.text = it.nutrients.carbs.toString()
                dialogFat.text = it.nutrients.fat.toString()
                dialogProtein.text = it.nutrients.protein.toString()
                viewModel.isBarcodeFoundDialogVisible.postValue(true)
            } else {
                viewModel.isBarcodeNotFoundDialogVisible.postValue(true)
            }
        })
        // endregion

        viewPagerAdapter = RecordMealPagerAdapter(supportFragmentManager, this, viewModel)
        record_meal_view_pager.adapter = viewPagerAdapter
        record_meal_tabs.setupWithViewPager(record_meal_view_pager)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.requestCodeScanBarcode) {
            val result: IntentResult? =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result?.contents != null)
                barcode = result.contents
            viewModel.findMealByBarcode(barcode)

        } else super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchView = menu!!.findItem(R.id.action_search).actionView as SearchView
        searchView.queryHint = getString(R.string.hint_find_food)
        searchView.setOnQueryTextListener(object :
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
                return true
            }
        })

        searchView.setOnCloseListener {
            getCurrentFragment().onSearchBoxCancel()
            true
        }

        searchView.setIconifiedByDefault(false)

        return true
    }

    private fun getCurrentFragment(): RecordMealFragment {
        val position = record_meal_view_pager.currentItem
        return viewPagerAdapter.getItem(position)
    }
}
