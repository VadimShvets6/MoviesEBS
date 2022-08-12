package com.top1shvetsvadim1.moviesebs.presentation.fragments.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.top1shvetsvadim1.moviesebs.domain.GetMoviesListUseCase
import com.top1shvetsvadim1.moviesebs.domain.MovieUIModel
import com.top1shvetsvadim1.moviesebs.domain.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getMoviesListUseCase: GetMoviesListUseCase,
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {

    private val _listMovies = MutableLiveData<PagingData<MovieUIModel>>()
    val listMovies: LiveData<PagingData<MovieUIModel>>
        get() = _listMovies

    private var job: Job = Job()

    fun listPaging() {
        job.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            getMoviesListUseCase().cachedIn(this).collect {
                _listMovies.postValue(it)
            }
        }
    }

    fun searchMovie(name: String) {
        job.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            searchMovieUseCase(name).cachedIn(this).collect {
                _listMovies.postValue(it)
            }
        }
    }
}