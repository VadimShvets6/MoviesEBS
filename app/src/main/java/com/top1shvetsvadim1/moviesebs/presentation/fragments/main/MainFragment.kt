package com.top1shvetsvadim1.moviesebs.presentation.fragments.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.top1shvetsvadim1.moviesebs.R
import com.top1shvetsvadim1.moviesebs.databinding.FragmentMainBinding
import com.top1shvetsvadim1.moviesebs.presentation.adapters.movie.MoviesAdapter
import com.top1shvetsvadim1.moviesebs.presentation.fragments.base.BaseFragment
import com.top1shvetsvadim1.moviesebs.utils.ResultOperator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    override fun getViewBinding(inflater: LayoutInflater): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater)
    }

    private val viewModel: MainFragmentViewModel by viewModels()

    private val movieAdapter by lazy {
        MoviesAdapter(::onMovieClicked)
    }

    private fun onMovieClicked(action: MoviesAdapter.ActionMovieAdapter) {
        when (action) {
            is MoviesAdapter.ActionMovieAdapter.OnMovieClicked -> {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailMovieFragment(
                        action.movie.tag
                    )
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelMethods()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModelObserves()
        searchMovies()
    }

    private fun searchMovies() {
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            private var jobSearch: Job = Job()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > 3) {
                    jobSearch.cancel()
                    jobSearch = lifecycleScope.launch(Dispatchers.IO) {
                        //TODO: reduce this time to 300-400
                        delay(500)
                        //TODO: try s.toString()
                        viewModel.searchMovie(s?.toString() ?: "")
                    }
                } else if (before == 1) {
                    jobSearch.cancel()
                    jobSearch = lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.searchMovie("")
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun viewModelObserves() {
        viewModel.listMovies.observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.IO) {
                movieAdapter.submitData(it)
            }
        }

        //TODO: move error handling to BaseFragment.
        //TODO: add an optional button "try again" when it is possible (logic permits)
        viewModel.error.observe(viewLifecycleOwner) {
            when (it) {
                is ResultOperator.Error -> {
                    //TODO: check class properly with :: or is
                    if (it.exception.javaClass.simpleName.equals("UnknownHostException")) {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.no_internet_conection),
                            Snackbar.LENGTH_LONG
                        )
                            .setBackgroundTint(
                                ContextCompat.getColor(
                                    requireActivity(),
                                    R.color.snack_bar_no_internet
                                )
                            )
                            .setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
                            .show()
                        binding.progressBar.isVisible = true
                    }
                }
            }
        }
    }


    private fun setupRecyclerView() {
        binding.rvListMovies.adapter = movieAdapter
        //TODO: better to unregister listener in OnDestroyView
        binding.rvListMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy < -80) {
                    //TODO: for these purposes FAB is recommended.
                    binding.goToUp.isVisible = true
                    binding.goToUp.setOnClickListener {
                        binding.rvListMovies.scrollToPosition(START_POSITION)
                        binding.goToUp.isVisible = false
                    }
                } else if (dy > 0) {
                    binding.goToUp.isVisible = false
                }
            }
        })
    }

    private fun viewModelMethods() {
        viewModel.listPaging()
    }

    //TODO: Ctrl + Alt + L
    companion object{
        //TODO: Unnecessary constant
        private const val START_POSITION = 0
    }
}