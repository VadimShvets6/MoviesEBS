package com.top1shvetsvadim1.moviesebs.domain

import com.top1shvetsvadim1.moviesebs.data.database.GenreItemDBModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMoviesList(): Flow<List<MovieEntity>>
    suspend fun getGenreList(): Flow<List<GenreItemDBModel>>
    suspend fun syncGenres()
    suspend fun getMovieDetail(id: Int): MovieEntity
    suspend fun getReviews(id: Int): List<ReviewUIModel>
    suspend fun searchMovie(name: String): Flow<List<MovieEntity>>
}