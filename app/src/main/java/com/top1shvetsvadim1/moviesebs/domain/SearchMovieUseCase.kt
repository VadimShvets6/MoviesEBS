package com.top1shvetsvadim1.moviesebs.domain

import androidx.paging.map
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//TODO: you can use this useCase with "" as param instead of getMoviesUseCase
class SearchMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(name: String) = repository.getMoviesList(name).map {
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