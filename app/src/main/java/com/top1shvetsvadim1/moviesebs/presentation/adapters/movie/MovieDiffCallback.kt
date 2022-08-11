package com.top1shvetsvadim1.moviesebs.presentation.adapters.movie

import androidx.recyclerview.widget.DiffUtil
import com.top1shvetsvadim1.moviesebs.domain.MovieUIModel
import com.top1shvetsvadim1.moviesebs.utils.MoviePayload

object MovieDiffCallback : DiffUtil.ItemCallback<MovieUIModel>() {
    override fun areItemsTheSame(oldItem: MovieUIModel, newItem: MovieUIModel): Boolean {
        return oldItem.tag == newItem.tag
    }

    override fun areContentsTheSame(oldItem: MovieUIModel, newItem: MovieUIModel): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: MovieUIModel, newItem: MovieUIModel): Any {
        val payloads = mutableListOf<MoviePayload>()

        if (oldItem.title != newItem.title) {
            payloads.add(MoviePayload.NameChanged(newName = newItem.title))
        }

        if (oldItem.picture != newItem.picture) {
            newItem.picture?.let { MoviePayload.PictureChange(newPicture = it) }
                ?.let { payloads.add(it) }
        }

        if (oldItem.rating != newItem.rating) {
            payloads.add(MoviePayload.RatingChange(newRating = newItem.rating.toFloat()))
        }

        if (oldItem.data != newItem.data) {
            payloads.add(MoviePayload.DataChange(newData = newItem.data))
        }

        return payloads
    }
}