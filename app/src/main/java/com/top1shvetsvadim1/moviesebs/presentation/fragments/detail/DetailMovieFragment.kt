package com.top1shvetsvadim1.moviesebs.presentation.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import com.top1shvetsvadim1.moviesebs.data.network.ApiService.Companion.BASE_IMAGE_URL
import com.top1shvetsvadim1.moviesebs.databinding.FragmentDetailMovieBinding
import com.top1shvetsvadim1.moviesebs.domain.ReviewUIModel
import com.top1shvetsvadim1.moviesebs.presentation.fragments.ViewPagerAdapter
import com.top1shvetsvadim1.moviesebs.presentation.fragments.description.DescriptionFragment
import com.top1shvetsvadim1.moviesebs.presentation.fragments.reviews.ReviewsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {

    private val args by navArgs<DetailMovieFragmentArgs>()

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding: FragmentDetailMovieBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailMovieBinding == null")

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var fragmentReview: ReviewsFragment
    private lateinit var fragmentDescription: DescriptionFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

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
                    tvMovieYear.text = releaseDate?.substring(0, 4) ?: "2001"
                    tvGenres.text = genres.joinToString(", ") { genres -> genres.name }
                }
            }
        }
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = ViewPagerAdapter(this, fragmentReview, fragmentDescription)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Description"
                }
                1 -> tab.text = "Reviews"
            }
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}