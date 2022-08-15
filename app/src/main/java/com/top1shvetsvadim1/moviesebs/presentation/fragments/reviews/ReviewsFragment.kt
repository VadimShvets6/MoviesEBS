package com.top1shvetsvadim1.moviesebs.presentation.fragments.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.top1shvetsvadim1.moviesebs.R
import com.top1shvetsvadim1.moviesebs.databinding.FragmentReviewsBinding
import com.top1shvetsvadim1.moviesebs.domain.ReviewUIModel
import com.top1shvetsvadim1.moviesebs.presentation.adapters.review.ReviewAdapter
import com.top1shvetsvadim1.moviesebs.presentation.fragments.base.BaseFragment
import com.top1shvetsvadim1.moviesebs.presentation.fragments.detail.DetailMovieFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsFragment : BaseFragment<FragmentReviewsBinding>() {

    override fun getViewBinding(inflater: LayoutInflater): FragmentReviewsBinding {
        return FragmentReviewsBinding.inflate(inflater)
    }

    private val movieImage by lazy {
        //TODO: unnecessary defaultValue in getString
        arguments?.getString(PARAM_MOVIE_IMAGE, "") ?: throw RuntimeException("Unknown url image")
    }
    private val movieName by lazy {
        //TODO: unnecessary defaultValue in getString
        arguments?.getString(PARAM_MOVIE_NAME, "") ?: throw RuntimeException("Unknown movie name")
    }
    private val reviews by lazy {
        arguments?.getParcelableArrayList<ReviewUIModel>(PARAM_REVIEWS)
    }

    private val reviewAdapter by lazy {
        ReviewAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupReviewList()
        setupRecyclerView()
        binding.buttonWriteReview.setOnClickListener {
            findNavController().navigate(
                //TODO: consider to name navigation actions simpler: to{FragmentDestination}
                DetailMovieFragmentDirections.actionDetailMovieFragmentToWriteReviewFragment(
                    movieImage,
                    movieName
                )
            )
        }
    }

    private fun setupReviewList() {
        if (reviews.isNullOrEmpty()) {
            binding.rvListReviews.isVisible = false
            binding.notReviews.apply {
                isVisible = true
                text = context.getString(R.string.no_reviews)
            }
        } else {
            reviewAdapter.submitList(reviews)
        }
    }

    private fun setupRecyclerView() {
        binding.rvListReviews.adapter = reviewAdapter
    }

    companion object {
        const val PARAM_MOVIE_IMAGE = "movie_image"
        const val PARAM_MOVIE_NAME = "movie_name"
        const val PARAM_REVIEWS = "reviews"

        //TODO: you do not need JvmStatic here.
        @JvmStatic
        fun newInstance(movieImage: String, movieName: String, reviews: ArrayList<ReviewUIModel>) =
            ReviewsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(PARAM_REVIEWS, reviews)
                    putString(PARAM_MOVIE_IMAGE, movieImage)
                    putString(PARAM_MOVIE_NAME, movieName)
                }
            }
    }
}