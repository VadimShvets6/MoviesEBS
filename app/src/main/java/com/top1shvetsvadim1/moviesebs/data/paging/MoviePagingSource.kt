package com.top1shvetsvadim1.moviesebs.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.top1shvetsvadim1.moviesebs.data.network.ApiService
import com.top1shvetsvadim1.moviesebs.domain.MovieItem

class MoviePagingSource constructor(
    private val apiService: ApiService,
    private val searchString: String
) : PagingSource<Int, MovieItem>() {

    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        return try {
            val currentPage = params.key ?: START_PAGE
            Log.d("Paging", "$currentPage")
            val result = if (searchString.isBlank()) {
                apiService.getMoviesList(page = currentPage).movies
            } else {
                apiService.getSearchMovieList(searchName = searchString, page = currentPage).movies
            }

            LoadResult.Page(
                data = result,
                prevKey = if (currentPage == START_PAGE) null else -1,
                nextKey = currentPage.plus(NEXT_PAGE)
            )

        } catch (e: Exception) {
            Log.d("Paging", " Error $e")
            LoadResult.Error(e)
        }
    }

    companion object{
        private const val START_PAGE = 1
        private const val NEXT_PAGE = 1
    }
}