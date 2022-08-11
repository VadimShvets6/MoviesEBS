package com.top1shvetsvadim1.moviesebs.presentation.fragments.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.top1shvetsvadim1.moviesebs.domain.GetMoviesListUseCase
import com.top1shvetsvadim1.moviesebs.domain.MovieUIModel
import com.top1shvetsvadim1.moviesebs.domain.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getMoviesListUseCase: GetMoviesListUseCase,
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {

    private val _listMovies = MutableLiveData<List<MovieUIModel>>()
    val listMovies: LiveData<List<MovieUIModel>>
        get() = _listMovies

    fun getMoviesList() {
        viewModelScope.launch(Dispatchers.IO) {
            getMoviesListUseCase().collectLatest {
                _listMovies.postValue(it)
            }
        }
    }

    fun searchMovie(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchMovieUseCase(name).collectLatest {
                _listMovies.postValue(it)
            }
        }
    }
}