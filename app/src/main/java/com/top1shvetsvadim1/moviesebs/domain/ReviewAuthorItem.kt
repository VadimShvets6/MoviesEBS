package com.top1shvetsvadim1.moviesebs.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ReviewAuthorItem(
    @Json(name = "name")
    val name: String,
    @Json(name = "username")
    val username: String,
    @Json(name = "avatar_path")
    val avatarPath: String?,
    @Json(name = "rating")
    val rating: Float?
) : Parcelable
