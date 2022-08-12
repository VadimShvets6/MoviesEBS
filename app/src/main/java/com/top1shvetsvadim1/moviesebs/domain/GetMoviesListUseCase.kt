package com.top1shvetsvadim1.moviesebs.domain

import androidx.paging.map
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesListUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke() = repository.getMoviesList("").map {
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
