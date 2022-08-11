package com.top1shvetsvadim1.moviesebs.presentation.fragments.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.top1shvetsvadim1.moviesebs.databinding.FragmentDescriptionBinding

class DescriptionFragment : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding: FragmentDescriptionBinding
        get() = _binding ?: throw RuntimeException("FragmentDescriptionBinding == null")

    private val args by lazy {
        arguments?.getString(PARAM_OVERVIEW, "") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.overview.text = args
    }

    companion object {
        const val PARAM_OVERVIEW = "overview"

        @JvmStatic
        fun newInstance(overview: String) =
            DescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAM_OVERVIEW, overview)
                }
            }
    }

}