package com.top1shvetsvadim1.moviesebs.domain

import com.squareup.moshi.Json

data class GenreItem(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
)