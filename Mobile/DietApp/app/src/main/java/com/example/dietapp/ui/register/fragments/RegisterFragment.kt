package com.example.dietapp.ui.register.fragments

import androidx.fragment.app.Fragment
import com.example.dietapp.ui.register.RegisterActivity
import com.example.dietapp.ui.register.RegisterViewModel

abstract class RegisterFragment : Fragment() {
    protected lateinit var viewModel: RegisterViewModel
    abstract fun validate(activity: RegisterActivity): Boolean
    abstract fun passData(activity: RegisterActivity)
}