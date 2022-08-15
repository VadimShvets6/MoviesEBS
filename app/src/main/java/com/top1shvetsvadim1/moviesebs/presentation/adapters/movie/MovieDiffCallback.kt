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
            //TODO: there can be UI misbehaviour when next item has no picture in it.
            newItem.picture?.let { MoviePayload.PictureChange(newPicture = it) }
                ?.let { payloads.add(it) }
        }

        if (oldItem.rating != newItem.rating) {
            payloads.add(MoviePayload.RatingChange(newRating = newItem.rating.toFloat()))
        }

        if (oldItem.data != newItem.data) {
            //TODO: there can be UI misbehaviour when next item has no data in it.
            newItem.data?.let { MoviePayload.DataChange(newData = it) }?.let { payloads.add(it) }
        }

        return payloads
    }
}