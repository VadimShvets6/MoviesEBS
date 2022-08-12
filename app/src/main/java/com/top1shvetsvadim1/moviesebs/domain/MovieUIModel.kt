package com.top1shvetsvadim1.moviesebs.domain

data class MovieUIModel(
    val tag: Int,
    val title: String,
    val picture: String?,
    val rating: Double,
    val data: String?,
    val genres: String
)