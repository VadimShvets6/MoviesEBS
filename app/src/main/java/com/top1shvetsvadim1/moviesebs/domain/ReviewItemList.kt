package com.top1shvetsvadim1.moviesebs.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewItemList(
    @Json(name = "total_results")
    val totalResults: Int,
    @Json(name = "results")
    val reviews: List<ReviewItem>
)
