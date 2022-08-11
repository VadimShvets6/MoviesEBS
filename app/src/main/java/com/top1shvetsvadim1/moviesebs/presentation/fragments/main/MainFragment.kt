package com.top1shvetsvadim1.moviesebs.presentation.fragments.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.top1shvetsvadim1.moviesebs.databinding.FragmentMainBinding
import com.top1shvetsvadim1.moviesebs.presentation.adapters.movie.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding == null")

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelMethods()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelObserves()
        setupRecyclerView()
        searchMovies()
    }

    private fun searchMovies() {
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            private var jobSearch: Job = Job()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > 3) {
                    jobSearch.cancel()
                    jobSearch = lifecycleScope.launch {
                        delay(500)
                        viewModel.searchMovie(s?.toString() ?: "")
                    }
                }
                if (s?.toString() == "") {
                    jobSearch.cancel()
                    jobSearch = lifecycleScope.launch {
                        delay(500)
                        viewModelMethods()
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun viewModelObserves() {
        viewModel.listMovies.observe(viewLifecycleOwner) {
            1
            movieAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        binding.rvListMovies.adapter = movieAdapter
    }

    private fun viewModelMethods() {
        viewModel.getMoviesList()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}