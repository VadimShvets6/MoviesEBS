package com.top1shvetsvadim1.moviesebs.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesItemsList(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val movies: List<MovieItem>
)