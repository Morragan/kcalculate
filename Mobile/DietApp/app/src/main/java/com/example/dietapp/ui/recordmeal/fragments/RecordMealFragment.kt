package com.example.dietapp.ui.recordmeal.fragments

import androidx.fragment.app.Fragment
import com.example.dietapp.ui.recordmeal.RecordMealAdapter
import com.example.dietapp.ui.recordmeal.RecordMealViewModel

abstract class RecordMealFragment : Fragment() {
    lateinit var adapter: RecordMealAdapter
    lateinit var viewModel: RecordMealViewModel

    abstract fun onQuerySubmit(query: String)
    abstract fun onQueryChange(query: String)
    abstract fun onSearchBoxCancel()
}