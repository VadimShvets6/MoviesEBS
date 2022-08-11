package com.top1shvetsvadim1.moviesebs.presentation.fragments.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.top1shvetsvadim1.moviesebs.domain.GetMovieDetailUseCase
import com.top1shvetsvadim1.moviesebs.domain.GetReviewsUseCase
import com.top1shvetsvadim1.moviesebs.domain.MovieEntity
import com.top1shvetsvadim1.moviesebs.domain.ReviewUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getReviewsUseCase: GetReviewsUseCase
) : ViewModel() {

    private val _movieItem = MutableLiveData<MovieEntity>()
    val movieItem: LiveData<MovieEntity>
        get() = _movieItem

    private val _listReview = MutableLiveData<List<ReviewUIModel>>()
    val listReview: LiveData<List<ReviewUIModel>>
        get() = _listReview

    fun getDetailMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getMovieDetailUseCase(id)
            _movieItem.postValue(result)
        }
    }

    fun getListReview(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getReviewsUseCase(id)
            _listReview.postValue(result)
        }
    }
}