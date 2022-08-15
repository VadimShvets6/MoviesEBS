package com.top1shvetsvadim1.moviesebs.presentation.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import com.top1shvetsvadim1.moviesebs.R
import com.top1shvetsvadim1.moviesebs.data.network.ApiService.Companion.BASE_IMAGE_URL
import com.top1shvetsvadim1.moviesebs.databinding.FragmentDetailMovieBinding
import com.top1shvetsvadim1.moviesebs.domain.ReviewUIModel
import com.top1shvetsvadim1.moviesebs.presentation.fragments.ViewPagerAdapter
import com.top1shvetsvadim1.moviesebs.presentation.fragments.base.BaseFragment
import com.top1shvetsvadim1.moviesebs.presentation.fragments.description.DescriptionFragment
import com.top1shvetsvadim1.moviesebs.presentation.fragments.reviews.ReviewsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : BaseFragment<FragmentDetailMovieBinding>() {

    private val args by navArgs<DetailMovieFragmentArgs>()

    override fun getViewBinding(inflater: LayoutInflater): FragmentDetailMovieBinding {
        return FragmentDetailMovieBinding.inflate(inflater)
    }

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var fragmentReview: ReviewsFragment
    private lateinit var fragmentDescription: DescriptionFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            getDetailMovie(args.id)
            viewModel.getListReview(args.id)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelObserves()
    }

    private fun viewModelObserves() {
        viewModel.movieItem.observe(viewLifecycleOwner) {
            with(binding) {
                with(it) {
                    fragmentDescription = DescriptionFragment.newInstance(it.overview)
                    viewModel.listReview.observe(viewLifecycleOwner) {
                        val result = ArrayList<ReviewUIModel>()
                        result.addAll(it)
                        fragmentReview = ReviewsFragment.newInstance(
                            posterPath ?: "",
                            title,
                            result
                        )
                        setupViewPager()
                    }
                    movieLogo.load(BASE_IMAGE_URL + posterPath)
                    ratingBarMovie.rating = (voteAverage / 2).toFloat()
                    tvMovieRating.text = "$voteAverage".substring(0, 3)
                    tvMovieTitle.text = title
                    tvMovieYear.text = releaseDate?.substring(0, 4) ?: DEFAULT_RELEASE_DATA
                    tvGenres.text = genres.joinToString(", ") { genres -> genres.name }
                }
            }
        }
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = ViewPagerAdapter(this, fragmentReview, fragmentDescription)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                FIRST_FRAGMENT -> {
                    tab.text = getString(R.string.description)
                }
                SECOND_FRAGMENT -> tab.text = getString(R.string.reviews)
            }
        }.attach()
    }

    companion object{
        private const val FIRST_FRAGMENT = 0
        private const val SECOND_FRAGMENT = 1
        private const val DEFAULT_RELEASE_DATA = "2001"
    }
}