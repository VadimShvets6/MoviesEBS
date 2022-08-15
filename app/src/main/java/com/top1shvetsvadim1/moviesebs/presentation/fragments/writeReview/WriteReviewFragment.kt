package com.top1shvetsvadim1.moviesebs.presentation.fragments.writeReview

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.top1shvetsvadim1.moviesebs.R
import com.top1shvetsvadim1.moviesebs.data.network.ApiService.Companion.BASE_IMAGE_URL
import com.top1shvetsvadim1.moviesebs.databinding.FragmentWriteReviewBinding
import com.top1shvetsvadim1.moviesebs.presentation.fragments.base.BaseFragment
import com.top1shvetsvadim1.moviesebs.presentation.fragments.reviews.ReviewsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WriteReviewFragment : BaseFragment<FragmentWriteReviewBinding>() {

    private val args by navArgs<WriteReviewFragmentArgs>()

    override fun getViewBinding(inflater: LayoutInflater): FragmentWriteReviewBinding {
        return FragmentWriteReviewBinding.inflate(inflater)
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
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO: edit text should handle focusable state by itself
                binding.editTextReview.isFocusable = true
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.editTextReview.isFocusable) {
                    binding.appBar.setExpanded(false)
                }
                makeButtonActive()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun editTextReviewListener() {
        //TODO: try to use lambda listener
        binding.editTextReview.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.editTextReview.isFocusable = true
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.editTextReview.isFocusable) {
                    binding.appBar.setExpanded(false)
                }
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
                    lifecycleScope.launch(Dispatchers.Main) {
                        binding.progressBar.isVisible = true
                        delay(1000)
                        Snackbar.make(
                            binding.root,
                            context.getString(R.string.thansk_for_your_review), Snackbar.LENGTH_LONG
                        )
                            .setBackgroundTint(
                                ContextCompat.getColor(
                                    requireActivity(),
                                    R.color.snack_bar_bg_4CAF50
                                )
                            )
                            .setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
                            .show()
                        binding.progressBar.isVisible = false
                        delay(1000)
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    companion object {

        //TODO: static
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