package com.top1shvetsvadim1.moviesebs.presentation.adapters.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.top1shvetsvadim1.moviesebs.R
import com.top1shvetsvadim1.moviesebs.data.network.ApiService.Companion.BASE_IMAGE_URL
import com.top1shvetsvadim1.moviesebs.databinding.MovieItemBinding
import com.top1shvetsvadim1.moviesebs.domain.MovieUIModel
import com.top1shvetsvadim1.moviesebs.utils.MoviePayload

class MoviesAdapter(
    val onAction: (ActionMovieAdapter) -> Unit
) : PagingDataAdapter<MovieUIModel, MoviesAdapter.MoviesViewHolder>(MovieDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movieItem = getItem(position)
        if (movieItem != null) {
            Log.d("Paging", "$movieItem")
            holder.bind(movieItem)
        }
    }

    override fun onBindViewHolder(
        holder: MoviesViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val payload = payloads.firstOrNull() as List<MoviePayload>?

        if (payload.isNullOrEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            payload.forEach {
                when (it) {
                    is MoviePayload.NameChanged -> holder.setName(it.newName)
                    is MoviePayload.DataChange -> holder.setData(it.newData)
                    is MoviePayload.PictureChange -> holder.setPicture(it.newPicture)
                    is MoviePayload.RatingChange -> holder.setRating(it.newRating)
                }
            }
        }
    }

    inner class MoviesViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movieItem: MovieUIModel) {
            setName(movieItem.title)
            movieItem.picture?.let { setPicture(it) }
            setRating(movieItem.rating.toFloat())
            setData(movieItem.data)
            setGenres(movieItem.genres)
            setOnClickListener(movieItem)
            binding.constraint.startAnimation(
                AnimationUtils.loadAnimation(
                    binding.constraint.context,
                    R.anim.rv_item_anim
                )
            )
        }

        private fun setOnClickListener(movieItem: MovieUIModel) {
            binding.root.setOnClickListener {
                onAction(ActionMovieAdapter.OnMovieClicked(movieItem))
            }
        }

        private fun setGenres(genres: String) {
            binding.genres.text = genres
        }


        fun setName(name: String) {
            binding.tvMovieTitle.text = name
        }

        fun setPicture(picture: String) {
            binding.ivMovieLogo.load(BASE_IMAGE_URL + picture)
        }

        fun setRating(rating: Float) {
            binding.ratingBarMovie.rating = (rating / DIVIDE_RATING_BY_2)
            binding.tvMovieRating.text = "$rating"
        }

        fun setData(data: String?) {
            //TODO: better to hide date instead of put the wrong one
            val result = if (data.equals("")) {
                DEFAULT_YEAR
            } else {
                data?.substring(0, 4) ?: DEFAULT_YEAR
            }
            binding.tvMovieYear.text = result
        }
    }

    sealed interface ActionMovieAdapter {
        data class OnMovieClicked(val movie: MovieUIModel) : ActionMovieAdapter
    }

    companion object {
        //TODO: unnecessary constant
        private const val DIVIDE_RATING_BY_2 = 2
        private const val DEFAULT_YEAR = "2021"
    }
}