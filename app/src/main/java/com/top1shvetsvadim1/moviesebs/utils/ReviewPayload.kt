package com.top1shvetsvadim1.moviesebs.utils

sealed interface ReviewPayload {
    data class PhotoUserAuthorChange(val newPhotoUser: String) : ReviewPayload
    data class AuthorNameChange(val newName: String) : ReviewPayload
    data class UpdateAtChange(val newUpdateAt: String) : ReviewPayload
    data class ContentReviewChange(val newContent: String) : ReviewPayload
}