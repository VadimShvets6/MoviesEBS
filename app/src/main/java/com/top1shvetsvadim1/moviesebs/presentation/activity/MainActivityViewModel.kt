package com.top1shvetsvadim1.moviesebs.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.top1shvetsvadim1.moviesebs.domain.GetGenreListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getGenreListUseCase: GetGenreListUseCase
) : ViewModel() {

    fun getGenreList() {
        viewModelScope.launch(Dispatchers.IO) {
            getGenreListUseCase()
        }
    }
}