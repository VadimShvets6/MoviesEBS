package com.top1shvetsvadim1.moviesebs.data.database

import com.top1shvetsvadim1.moviesebs.domain.GenreItem
import javax.inject.Inject

class Mapper @Inject constructor() {

    private fun mapGenreItemEntityToGenreDBModel(genreItem: GenreItem) = GenreItemDBModel(
        id = genreItem.id,
        name = genreItem.name
    )

    fun mapGenreListEntityToGenreListDBModel(list: List<GenreItem>) = list.map {
        mapGenreItemEntityToGenreDBModel(it)
    }

}