package com.top1shvetsvadim1.moviesebs.domain

import com.squareup.moshi.Json

data class GenreList(
    @Json(name = "genres")
    val genres: List<GenreItem>
)