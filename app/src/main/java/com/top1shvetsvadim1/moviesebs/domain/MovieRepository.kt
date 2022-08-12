package com.top1shvetsvadim1.moviesebs.domain

import androidx.paging.PagingData
import com.top1shvetsvadim1.moviesebs.data.database.GenreItemDBModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMoviesList(searchString: String): Flow<PagingData<MovieEntity>>
    suspend fun getGenreList(): Flow<List<GenreItemDBModel>>
    suspend fun syncGenres()
    suspend fun getMovieDetail(id: Int): MovieEntity
    suspend fun getReviews(id: Int): List<ReviewUIModel>
}