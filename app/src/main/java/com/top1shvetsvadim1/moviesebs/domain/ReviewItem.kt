package com.top1shvetsvadim1.moviesebs.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewItem(
    @Json(name = "author")
    val author: String,
    @Json(name = "author_details")
    val authorDetails: ReviewAuthorItem,
    @Json(name = "content")
    val content: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "updated_at")
    val updateAt: String,
    @Json(name = "id")
    val id: String
)
