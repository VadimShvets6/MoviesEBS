package com.top1shvetsvadim1.moviesebs.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.top1shvetsvadim1.moviesebs.data.network.ApiService
import com.top1shvetsvadim1.moviesebs.domain.MovieEntity
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val apiService: ApiService
) : PagingSource<Int, MovieEntity>() {

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        TODO("Not yet implemented")
    }
}