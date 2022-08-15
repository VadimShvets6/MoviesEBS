package com.top1shvetsvadim1.moviesebs.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.top1shvetsvadim1.moviesebs.domain.GetGenreListUseCase
import com.top1shvetsvadim1.moviesebs.presentation.fragments.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getGenreListUseCase: GetGenreListUseCase
) : BaseViewModel() {

    fun getGenreList() {
        //TODO: you've forgotten
        /*viewModelScope.launchDetail {
            getGenreListUseCase()
        }*/


        //TODO: throw exception on no internet connection
        viewModelScope.launch(Dispatchers.IO) {
            getGenreListUseCase()
        }
    }
}