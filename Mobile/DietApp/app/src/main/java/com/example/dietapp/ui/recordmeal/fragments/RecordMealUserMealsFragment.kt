package com.example.dietapp.ui.recordmeal.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.ui.recordmeal.RecordMealAdapter
import com.example.dietapp.ui.recordmeal.RecordMealViewModel
import com.example.dietapp.utils.ViewState.*
import kotlinx.android.synthetic.main.fragment_record_meal_my_meals.*

/**
 * A simple [Fragment] subclass.
 */
class RecordMealUserMealsFragment : RecordMealFragment() {

    companion object {
        @JvmStatic
        fun newInstance(_adapter: RecordMealAdapter, _viewModel: RecordMealViewModel) =
            RecordMealUserMealsFragment().apply {
                adapter = _adapter
                viewModel = _viewModel
            }
    }

    override fun onQueryChange(query: String) {
        adapter.filter.filter(query)
    }

    override fun onQuerySubmit(query: String) {}

    override fun onSearchBoxCancel() {
        adapter.filter.filter("")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_record_meal_my_meals, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerView =
            view!!.findViewById<RecyclerView>(R.id.record_meal_recycler_view_my_meals)
//        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        viewModel.userMeals.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                viewModel.userMealsFragmentViewState.value = EMPTY
            } else {
                adapter.replaceMeals(it)
                viewModel.userMealsFragmentViewState.value = ITEMS
            }
        })

        viewModel.userMealsFragmentViewState.observe(viewLifecycleOwner, Observer {
            @Suppress("NON_EXHAUSTIVE_WHEN")
            when (it) {
                LOADING -> {
                    record_meal_recycler_view_my_meals.visibility = View.GONE
                    record_meal_placeholder_my_meals_loading.visibility = View.VISIBLE
                    record_meal_placeholder_my_meals_empty.visibility = View.GONE
                }
                EMPTY -> {
                    record_meal_recycler_view_my_meals.visibility = View.GONE
                    record_meal_placeholder_my_meals_loading.visibility = View.GONE
                    record_meal_placeholder_my_meals_empty.visibility = View.VISIBLE
                }
                ITEMS -> {
                    record_meal_recycler_view_my_meals.visibility = View.VISIBLE
                    record_meal_placeholder_my_meals_loading.visibility = View.GONE
                    record_meal_placeholder_my_meals_empty.visibility = View.GONE
                }
            }
        })
    }
}
