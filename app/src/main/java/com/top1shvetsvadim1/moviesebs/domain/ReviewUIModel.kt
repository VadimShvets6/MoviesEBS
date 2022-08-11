package com.top1shvetsvadim1.moviesebs.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewUIModel(
    val authorDetails: ReviewAuthorItem,
    val content: String,
    val updateAt: String,
    val id: String
) : Parcelable