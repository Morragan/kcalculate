package com.example.dietapp.ui.recordmeal.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.ui.recordmeal.RecordMealAdapter
import com.example.dietapp.ui.recordmeal.RecordMealViewModel
import com.example.dietapp.utils.ViewState.*
import kotlinx.android.synthetic.main.fragment_record_meal_all_meals.*

class RecordMealAllMealsFragment : RecordMealFragment() {

    companion object {
        @JvmStatic
        fun newInstance(_adapter: RecordMealAdapter, _viewModel: RecordMealViewModel) =
            RecordMealAllMealsFragment().apply {
                adapter = _adapter
                viewModel = _viewModel
            }
    }

    override fun onQuerySubmit(query: String) {
        viewModel.allMealsFragmentViewState.value = LOADING
        viewModel.find3rdPartyMeals(query)
    }

    override fun onQueryChange(query: String) {}

    override fun onSearchBoxCancel() {
        adapter.filter.filter("")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_record_meal_all_meals, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerView = view!!.findViewById<RecyclerView>(R.id.record_meal_recycler_view_all_meals)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        viewModel.found3rdPartyMeals.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()){
                viewModel.allMealsFragmentViewState.value = EMPTY
            }
            else{
                adapter.replaceMeals(it)
                viewModel.allMealsFragmentViewState.value = ITEMS
            }
        })

        viewModel.allMealsFragmentViewState.observe(viewLifecycleOwner, Observer {
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when(it){
                DEFAULT -> {
                    record_meal_recycler_view_all_meals.visibility = View.GONE
                    record_meal_placeholder_all_meals.visibility = View.VISIBLE
                    record_meal_placeholder_all_meals_empty.visibility = View.GONE
                    record_meal_placeholder_all_meals_loading.visibility = View.GONE
                }
                LOADING ->{
                    record_meal_recycler_view_all_meals.visibility = View.GONE
                    record_meal_placeholder_all_meals.visibility = View.GONE
                    record_meal_placeholder_all_meals_empty.visibility = View.GONE
                    record_meal_placeholder_all_meals_loading.visibility = View.VISIBLE
                }
                EMPTY ->{
                    record_meal_recycler_view_all_meals.visibility = View.GONE
                    record_meal_placeholder_all_meals.visibility = View.GONE
                    record_meal_placeholder_all_meals_empty.visibility = View.VISIBLE
                    record_meal_placeholder_all_meals_loading.visibility = View.GONE
                }
                ITEMS ->{
                    record_meal_recycler_view_all_meals.visibility = View.VISIBLE
                    record_meal_placeholder_all_meals.visibility = View.GONE
                    record_meal_placeholder_all_meals_empty.visibility = View.GONE
                    record_meal_placeholder_all_meals_loading.visibility = View.GONE
                }
            }
        })
    }
}
