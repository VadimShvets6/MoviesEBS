package com.top1shvetsvadim1.moviesebs.presentation.fragments.writeReview

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.top1shvetsvadim1.moviesebs.R
import com.top1shvetsvadim1.moviesebs.data.network.ApiService.Companion.BASE_IMAGE_URL
import com.top1shvetsvadim1.moviesebs.databinding.FragmentWriteReviewBinding
import com.top1shvetsvadim1.moviesebs.presentation.fragments.reviews.ReviewsFragment

class WriteReviewFragment : Fragment() {

    private val args by navArgs<WriteReviewFragmentArgs>()

    private var _binding: FragmentWriteReviewBinding? = null
    private val binding: FragmentWriteReviewBinding
        get() = _binding ?: throw RuntimeException("FragmentWriteReviewBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWriteReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            movieLogo.load(BASE_IMAGE_URL + args.movieIamge)
            movieTitle.text = args.movieName
        }
        editTextReviewListener()
        editTextTitleListener()
        ratingBarListener()

    }

    private fun ratingBarListener() {
        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->

            if (rating > 0) {
                makeButtonActive()
            }
        }
    }

    private fun editTextTitleListener() {
        binding.editTextTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                makeButtonActive()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun editTextReviewListener() {
        binding.editTextReview.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                makeButtonActive()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun makeButtonActive() {
        if (!binding.editTextReview.text.isNullOrEmpty() && !binding.editTextTitle.text.isNullOrEmpty() && binding.ratingBar.rating > 0) {
            with(binding.buttonWriteReview) {
                isEnabled = true
                setOnClickListener {
                    Snackbar.make(binding.root, "Thanks for your review", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.snack_bar_bg_4CAF50
                            )
                        )
                        .setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
                        .show()
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(movieImage: String, movieName: String) =
            ReviewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ReviewsFragment.PARAM_MOVIE_IMAGE, movieImage)
                    putString(ReviewsFragment.PARAM_MOVIE_NAME, movieName)
                }
            }
    }
}