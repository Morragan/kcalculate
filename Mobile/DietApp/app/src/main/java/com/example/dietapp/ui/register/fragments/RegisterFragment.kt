package com.example.dietapp.ui.register.fragments

import androidx.fragment.app.Fragment

abstract class RegisterFragment: Fragment() {
    abstract fun validate(): Boolean
    abstract fun passData()
}