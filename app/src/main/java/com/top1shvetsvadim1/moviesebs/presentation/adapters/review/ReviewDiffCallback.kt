package com.top1shvetsvadim1.moviesebs.presentation.adapters.review

import androidx.recyclerview.widget.DiffUtil
import com.top1shvetsvadim1.moviesebs.domain.ReviewUIModel
import com.top1shvetsvadim1.moviesebs.utils.ReviewPayload

object ReviewDiffCallback : DiffUtil.ItemCallback<ReviewUIModel>() {
    override fun areItemsTheSame(oldItem: ReviewUIModel, newItem: ReviewUIModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReviewUIModel, newItem: ReviewUIModel): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: ReviewUIModel, newItem: ReviewUIModel): Any? {
        val payloads = mutableListOf<ReviewPayload>()

        if (oldItem.authorDetails.avatarPath != newItem.authorDetails.avatarPath) {
            newItem.authorDetails.avatarPath?.let { ReviewPayload.PhotoUserAuthorChange(newPhotoUser = it) }
                ?.let { payloads.add(it) }
        }

        if (oldItem.authorDetails.username != newItem.authorDetails.username) {
            payloads.add(ReviewPayload.AuthorNameChange(newName = newItem.authorDetails.username))
        }

        if (oldItem.updateAt != newItem.updateAt) {
            payloads.add(ReviewPayload.UpdateAtChange(newUpdateAt = newItem.updateAt))
        }

        if (oldItem.content != newItem.content) {
            payloads.add(ReviewPayload.ContentReviewChange(newContent = newItem.content))
        }

        return payloads
    }
}