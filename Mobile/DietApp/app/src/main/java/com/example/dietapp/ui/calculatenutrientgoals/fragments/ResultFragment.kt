package com.example.dietapp.ui.calculatenutrientgoals.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dietapp.databinding.FragmentResultBinding
import com.example.dietapp.ui.calculatenutrientgoals.CalculateNutrientGoalsViewModel

/**
 * A simple [Fragment] subclass.
 */
class ResultFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance(_viewModel: CalculateNutrientGoalsViewModel) =
            ResultFragment().apply {
                viewModel = _viewModel
            }
    }

    override fun validate() = true

    override fun passData() {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentResultBinding =
            FragmentResultBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }
}
