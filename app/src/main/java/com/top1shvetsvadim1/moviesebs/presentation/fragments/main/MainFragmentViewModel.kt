package com.top1shvetsvadim1.moviesebs.presentation.fragments.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.top1shvetsvadim1.moviesebs.domain.GetMoviesListUseCase
import com.top1shvetsvadim1.moviesebs.domain.MovieUIModel
import com.top1shvetsvadim1.moviesebs.domain.SearchMovieUseCase
import com.top1shvetsvadim1.moviesebs.presentation.fragments.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getMoviesListUseCase: GetMoviesListUseCase,
    private val searchMovieUseCase: SearchMovieUseCase
) : BaseViewModel() {

    private val _listMovies = MutableLiveData<PagingData<MovieUIModel>>()
    val listMovies: LiveData<PagingData<MovieUIModel>>
        get() = _listMovies

    private var job: Job = Job()

    fun listPaging() {
        job.cancel()
        job = viewModelScope.launchSafe(_listMovies) {
            getMoviesListUseCase().cachedIn(this)
        }
    }

    fun searchMovie(name: String) {
        job.cancel()
        job = viewModelScope.launchSafe(_listMovies) {
            searchMovieUseCase(name).cachedIn(this)
        }
    }
}