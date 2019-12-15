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
        val view = inflater.inflate(R.layout.fragment_record_meal_all_meals, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.record_meal_recycler_view_all_meals)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        viewModel.found3rdPartyMeals.observe(viewLifecycleOwner, Observer { meals ->
            adapter.replaceMeals(meals)
            if (!meals.isNullOrEmpty()) {
                record_meal_recycler_view_all_meals.visibility = View.VISIBLE
                record_meal_placeholder_all_meals.visibility = View.GONE
            }
        })

        return view
    }
}
