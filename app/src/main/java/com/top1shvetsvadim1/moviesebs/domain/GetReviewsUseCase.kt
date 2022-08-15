package com.top1shvetsvadim1.moviesebs.domain

import javax.inject.Inject

class GetReviewsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(id: Int) = repository.getReviews(id).map {
        //TODO: why do you map ReviewUIModel into ReviewUIModel?
        ReviewUIModel(
            authorDetails = ReviewAuthorItem(
                name = it.authorDetails.name,
                username = it.authorDetails.username,
                avatarPath = it.authorDetails.avatarPath,
                rating = it.authorDetails.rating
            ), //TODO: newLines !!!!!!!!
            content = it.content, updateAt = it.updateAt, id = it.id
        )
    }
}