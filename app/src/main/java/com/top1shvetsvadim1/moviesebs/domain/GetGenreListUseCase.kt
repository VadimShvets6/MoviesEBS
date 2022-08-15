package com.top1shvetsvadim1.moviesebs.domain

import javax.inject.Inject

//TODO: rename get to fetch
class GetGenreListUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke() = repository.getGenreList()
}