package com.example.dietapp.ui.register.fragments

import androidx.fragment.app.Fragment
import com.example.dietapp.ui.register.RegisterActivity

abstract class RegisterFragment : Fragment() {
    abstract fun validate(activity: RegisterActivity): Boolean
    abstract fun passData(activity: RegisterActivity)
}