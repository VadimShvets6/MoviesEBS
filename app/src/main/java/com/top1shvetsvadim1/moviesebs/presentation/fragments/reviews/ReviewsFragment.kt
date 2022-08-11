package com.top1shvetsvadim1.moviesebs.presentation.fragments.reviews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.top1shvetsvadim1.moviesebs.databinding.FragmentReviewsBinding
import com.top1shvetsvadim1.moviesebs.domain.ReviewUIModel
import com.top1shvetsvadim1.moviesebs.presentation.adapters.review.ReviewAdapter
import com.top1shvetsvadim1.moviesebs.presentation.fragments.detail.DetailMovieFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsFragment : Fragment() {

    private var _binding: FragmentReviewsBinding? = null
    private val binding: FragmentReviewsBinding
        get() = _binding ?: throw RuntimeException("FragmentReviewsBinding == null")

    private val movieImage by lazy {
        arguments?.getString(PARAM_MOVIE_IMAGE, "") ?: throw RuntimeException("Unknown url image")
    }
    private val movieName by lazy {
        arguments?.getString(PARAM_MOVIE_NAME, "") ?: throw RuntimeException("Unknown movie name")
    }
    private val reviews by lazy {
        arguments?.getParcelableArrayList<ReviewUIModel>("test")
    }

    private val reviewAdapter by lazy {
        ReviewAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // viewModel.getListReview(movieId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupReviewList()
        setupRecyclerView()
        Log.d("MainREV", "$reviews")
        binding.buttonWriteReview.setOnClickListener {
            findNavController().navigate(
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
                text = "No reviews"
            }
        } else {
            reviewAdapter.submitList(reviews)
        }
    }

    private fun setupRecyclerView() {
        binding.rvListReviews.adapter = reviewAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val PARAM_MOVIE_IMAGE = "movie_image"
        const val PARAM_MOVIE_NAME = "movie_name"

        @JvmStatic
        fun newInstance(movieImage: String, movieName: String, reviews: ArrayList<ReviewUIModel>) =
            ReviewsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("test", reviews)
                    putString(PARAM_MOVIE_IMAGE, movieImage)
                    putString(PARAM_MOVIE_NAME, movieName)
                }
            }
    }
}