package com.top1shvetsvadim1.moviesebs.presentation.fragments.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.top1shvetsvadim1.moviesebs.domain.GetMovieDetailUseCase
import com.top1shvetsvadim1.moviesebs.domain.GetReviewsUseCase
import com.top1shvetsvadim1.moviesebs.domain.MovieEntity
import com.top1shvetsvadim1.moviesebs.domain.ReviewUIModel
import com.top1shvetsvadim1.moviesebs.presentation.fragments.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getReviewsUseCase: GetReviewsUseCase
) : BaseViewModel() {

    private val _movieItem = MutableLiveData<MovieEntity>()
    val movieItem: LiveData<MovieEntity>
        get() = _movieItem

    private val _listReview = MutableLiveData<List<ReviewUIModel>>()
    val listReview: LiveData<List<ReviewUIModel>>
        get() = _listReview

    //TODO: you do not need to save job in this case.
    private var job: Job = Job()

    fun getDetailMovie(id: Int) {
        job = viewModelScope.launchDetail {
            val result = getMovieDetailUseCase(id)
            _movieItem.postValue(result)
        }
    }

    fun getListReview(id: Int) {
        job = viewModelScope.launchDetail {
            val result = getReviewsUseCase(id)
            _listReview.postValue(result)
        }
    }
}