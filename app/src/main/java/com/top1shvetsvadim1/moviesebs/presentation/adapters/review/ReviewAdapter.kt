package com.top1shvetsvadim1.moviesebs.presentation.adapters.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.top1shvetsvadim1.moviesebs.R
import com.top1shvetsvadim1.moviesebs.databinding.ReviewItemBinding
import com.top1shvetsvadim1.moviesebs.domain.ReviewUIModel
import com.top1shvetsvadim1.moviesebs.utils.ReviewPayload

class ReviewAdapter :
    ListAdapter<ReviewUIModel, ReviewAdapter.ReviewViewHolder>(ReviewDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ReviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val reviewItem = getItem(position)
        holder.bind(reviewItem)
    }

    override fun onBindViewHolder(
        holder: ReviewViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val payload = payloads.firstOrNull() as List<ReviewPayload>?

        if (payload.isNullOrEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            payload.forEach {
                when (it) {
                    is ReviewPayload.AuthorNameChange -> holder.setAuthorName(it.newName)
                    is ReviewPayload.ContentReviewChange -> holder.setContent(it.newContent)
                    is ReviewPayload.PhotoUserAuthorChange -> holder.setPhotoAuthor(it.newPhotoUser)
                    is ReviewPayload.UpdateAtChange -> holder.setData(it.newUpdateAt)
                }
            }
        }
    }

    inner class ReviewViewHolder(private val binding: ReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewItem: ReviewUIModel) {
            with(binding) {
                with(reviewItem) {
                    setAuthorName(authorDetails.username)
                    //TODO: try to parse date with one of date formatters
                    setData(updateAt.substring(0, 10))
                    setContent(content)
                    //TODO: you've already put DEFAULT_RATING as optional value in repo. Do it nonNullable in UIModel.
                    setRatingBar((authorDetails.rating?.div(2)) ?: DEFAULT_RATING)
                    setPhotoAuthor(authorDetails.avatarPath)
                }
            }
        }

        fun setAuthorName(name: String) {
            binding.reviewAuthor.text = name
        }

        fun setData(data: String) {
            binding.reviewData.text = data
        }

        fun setContent(content: String) {
            binding.review.text = content
        }

        private fun setRatingBar(rating: Float) {
            binding.ratingBarReview.rating = rating
        }

        fun setPhotoAuthor(photo: String?) {
            photo?.let {
                //TODO: move this logic (link parsing) to other class or extensions. Solid.
                if (it.length == INCOMPLETE_URL) {
                    binding.photoAuthor.load(BASE_AUTHOR_PHOTO_URL + photo)
                } else {
                    binding.photoAuthor.load(photo.substring(1))
                }
            } ?: binding.photoAuthor.load(R.drawable.ic_search)
        }
    }

    companion object {
        //TODO: hardcoded constant. API can change its base URL and your constant will not work.
        private const val INCOMPLETE_URL = 32
        private const val BASE_AUTHOR_PHOTO_URL = "https://www.gravatar.com/avatar"
        private const val DEFAULT_RATING = 0f
    }
}