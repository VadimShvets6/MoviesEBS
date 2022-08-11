package com.top1shvetsvadim1.moviesebs.utils

sealed interface MoviePayload {
    data class NameChanged(val newName: String) : MoviePayload
    data class PictureChange(val newPicture: String) : MoviePayload
    data class RatingChange(val newRating: Float) : MoviePayload
    data class DataChange(val newData: String) : MoviePayload
}