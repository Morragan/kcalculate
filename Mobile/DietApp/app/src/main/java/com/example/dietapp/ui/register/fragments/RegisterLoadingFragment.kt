package com.example.dietapp.ui.register.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.dietapp.R
import com.example.dietapp.ui.register.RegisterActivity
import com.example.dietapp.ui.register.RegisterViewModel
import kotlinx.android.synthetic.main.fragment_register_loading.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterLoadingFragment : RegisterFragment() {

    companion object {
        fun newInstance(_viewModel: RegisterViewModel) =
            RegisterLoadingFragment().apply {
                viewModel = _viewModel
            }
    }

    override fun validate(activity: RegisterActivity) = true

    override fun passData(activity: RegisterActivity) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_loading, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.isLoadingFragmentLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                register_button_try_again.visibility = View.GONE
                register_progress_bar_loading.visibility = View.VISIBLE
            } else {
                register_button_try_again.visibility = View.VISIBLE
                register_progress_bar_loading.visibility = View.GONE
            }
        })
    }
}
