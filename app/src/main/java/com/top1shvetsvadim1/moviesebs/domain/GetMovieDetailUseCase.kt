package com.top1shvetsvadim1.moviesebs.domain

import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(id: Int) = repository.getMovieDetail(id)
}