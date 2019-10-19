package com.example.dietapp.ui.home

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dietapp.DietApp
import com.example.dietapp.R
import com.example.dietapp.ui.login.LoginActivity
import com.example.dietapp.ui.nointernet.NoInternetActivity
import com.example.dietapp.ui.recordmeal.RecordMealActivity
import com.example.dietapp.utils.Methods
import kotlinx.android.synthetic.main.activity_home.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), HomeView {

    @Inject
    lateinit var presenter: HomePresenter

    override fun showConnectionError() {
        startActivityForResult(Intent(this, NoInternetActivity::class.java), -1)
    }

    override fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun changeDay(date: Date) {
        when {
            Methods.datesAreTheSameDay(date, Date()) -> home_text_day.setText(R.string.today)
            Methods.datesAreTheSameDay(date, Methods.offsetDate(Date(), -1)) -> home_text_day.setText(
                R.string.yesterday
            )
            else -> {
                val formatter = SimpleDateFormat("EEE, d MMM yy", Locale.getDefault())
                home_text_day.text = formatter.format(date)
            }
        }
    }

    override fun updateKcalProgress(progress: Float, goal: Int, eaten: Int, left: Int, progressBarColorId: Int) {
        home_cpb.progress = progress
        home_text_goal.text = goal.toString()
        home_text_eaten.text = eaten.toString()
        home_text_left.text = left.toString()
        home_cpb.progressBarColor = progressBarColorId
    }

    override fun updateCarbsProgress(progress: Float, goal: Int, eaten: Int, left: Int, progressBarColorId: Int) {
        home_progress_bar_carbs.progress = progress.toInt()
        home_text_carbs_goal.text = goal.toString()
        home_text_carbs_eaten.text = eaten.toString()
        home_text_carbs_left.text = left.toString()
        home_progress_bar_carbs.progressDrawable.setColorFilter(progressBarColorId, PorterDuff.Mode.SRC_IN)
    }

    override fun updateFatProgress(progress: Float, goal: Int, eaten: Int, left: Int, progressBarColorId: Int) {
        home_progress_bar_fats.progress = progress.toInt()
        home_text_fats_goal.text = goal.toString()
        home_text_fats_eaten.text = eaten.toString()
        home_text_fats_left.text = left.toString()
        home_progress_bar_fats.progressDrawable.setColorFilter(progressBarColorId, PorterDuff.Mode.SRC_IN)
    }

    override fun updateProteinProgress(progress: Float, goal: Int, eaten: Int, left: Int, progressBarColorId: Int) {
        home_progress_bar_proteins.progress = progress.toInt()
        home_text_proteins_goal.text = goal.toString()
        home_text_proteins_eaten.text = eaten.toString()
        home_text_proteins_left.text = left.toString()
        home_progress_bar_proteins.progressDrawable.setColorFilter(progressBarColorId, PorterDuff.Mode.SRC_IN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val activityComponent = (application as DietApp).appComponent.newActivityComponent()
        activityComponent.inject(this)

        // TODO: skeletonize

        presenter.loadData()

        home_add_fab.setOnClickListener {
            startActivity(Intent(this, RecordMealActivity::class.java))
        }

        home_button_day_back.setOnClickListener {
            presenter.onDateButtonBackward()
        }

        home_button_day_forward.setOnClickListener {
            presenter.onDateButtonForward()
        }

        home_navigation.setOnNavigationItemSelectedListener {
            // TODO: navigate
            val id = it.itemId
            return@setOnNavigationItemSelectedListener if (id == R.id.app_bar_profile) {
                startActivity(Intent(this, this::class.java))
                true
            } else
                false

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
