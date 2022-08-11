package com.top1shvetsvadim1.moviesebs.domain

import com.top1shvetsvadim1.moviesebs.data.database.GenreItemDBModel


data class MovieEntity(
    val id: Int,
    val posterPath: String?,
    val overview: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val genres: List<GenreItemDBModel>
)