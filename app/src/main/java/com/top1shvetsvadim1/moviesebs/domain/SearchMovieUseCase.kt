package com.top1shvetsvadim1.moviesebs.domain

import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(name: String) = repository.searchMovie(name).map {
        it.map { entity ->
            MovieUIModel(
                tag = entity.id,
                title = entity.title,
                picture = entity.posterPath,
                rating = entity.voteAverage,
                data = entity.releaseDate,
                genres = entity.genres.joinToString(", ") { genres -> genres.name }
            )
        }
    }
}