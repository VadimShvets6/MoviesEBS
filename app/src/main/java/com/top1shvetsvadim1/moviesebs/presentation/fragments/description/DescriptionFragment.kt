package com.top1shvetsvadim1.moviesebs.presentation.fragments.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.top1shvetsvadim1.moviesebs.databinding.FragmentDescriptionBinding
import com.top1shvetsvadim1.moviesebs.presentation.fragments.base.BaseFragment

class DescriptionFragment : BaseFragment<FragmentDescriptionBinding>() {

    override fun getViewBinding(inflater: LayoutInflater): FragmentDescriptionBinding {
        return FragmentDescriptionBinding.inflate(inflater)
    }

    private val args by lazy {
        arguments?.getString(PARAM_OVERVIEW, DEFAULT_VALUE) ?: DEFAULT_VALUE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.overview.text = args

    }

    companion object {
        const val PARAM_OVERVIEW = "overview"
        private const val DEFAULT_VALUE = ""

        @JvmStatic
        fun newInstance(overview: String) =
            DescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAM_OVERVIEW, overview)
                }
            }
    }

}