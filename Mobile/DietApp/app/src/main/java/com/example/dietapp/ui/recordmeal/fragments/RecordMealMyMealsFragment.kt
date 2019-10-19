package com.example.dietapp.ui.recordmeal.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.ui.recordmeal.RecordMealAdapter

/**
 * A simple [Fragment] subclass.
 */
class RecordMealMyMealsFragment : RecordMealFragment() {

    companion object {
        @JvmStatic
        fun newInstance(_adapter: RecordMealAdapter) =
            RecordMealMyMealsFragment().apply {
                adapter = _adapter
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_record_meal_my_meals, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.record_meal_recycler_view_my_meals)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        // Inflate the layout for this fragment
        return view
    }
}
