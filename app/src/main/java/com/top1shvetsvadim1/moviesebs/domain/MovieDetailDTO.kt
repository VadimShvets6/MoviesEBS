package com.top1shvetsvadim1.moviesebs.domain

import com.squareup.moshi.Json
import com.top1shvetsvadim1.moviesebs.data.database.GenreItemDBModel

data class MovieDetailDTO(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "genres")
    val genres: List<GenreItemDBModel>,
    @Json(name = "id")
    val id: Int,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "vote_average")
    val voteAverage: Double
)
