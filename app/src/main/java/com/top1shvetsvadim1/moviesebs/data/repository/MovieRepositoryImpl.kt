package com.top1shvetsvadim1.moviesebs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.top1shvetsvadim1.moviesebs.data.database.GenreItemDBModel
import com.top1shvetsvadim1.moviesebs.data.database.GenreItemDao
import com.top1shvetsvadim1.moviesebs.data.database.Mapper
import com.top1shvetsvadim1.moviesebs.data.network.ApiService
import com.top1shvetsvadim1.moviesebs.data.paging.MoviePagingSource
import com.top1shvetsvadim1.moviesebs.domain.MovieEntity
import com.top1shvetsvadim1.moviesebs.domain.MovieRepository
import com.top1shvetsvadim1.moviesebs.domain.ReviewAuthorItem
import com.top1shvetsvadim1.moviesebs.domain.ReviewUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val genreItemDao: GenreItemDao,
    private val mapper: Mapper
) : MovieRepository {

    private fun getPagerMovies(searchString: String) = Pager(
        PagingConfig(PAGE_SIZE_20),
        pagingSourceFactory = { MoviePagingSource(apiService, searchString) }
    ).flow


    override suspend fun getMoviesList(searchString: String): Flow<PagingData<MovieEntity>> {
        return combine(
            getPagerMovies(searchString),
            getGenreList()
        ) { movies, genres ->
            movies.map {
                MovieEntity(
                    id = it.id,
                    posterPath = it.posterPath,
                    overview = it.overview,
                    releaseDate = it.releaseDate,
                    title = it.title,
                    voteAverage = it.voteAverage,
                    genres = genres.filter { genre ->
                        genre.id in it.genreIds
                    }
                )
            }
        }
    }

    override suspend fun syncGenres() {
        val mapped = mapper.mapGenreListEntityToGenreListDBModel(apiService.getGenreList().genres)
        genreItemDao.addGenreList(mapped)
    }

    override suspend fun getGenreList(): Flow<List<GenreItemDBModel>> {
        syncGenres()
        return genreItemDao.getGenreList()
    }

    override suspend fun getMovieDetail(id: Int): MovieEntity {
        val movieDTO = apiService.getMovieDetail(id)
        return MovieEntity(
            id = movieDTO.id,
            posterPath = movieDTO.posterPath,
            overview = movieDTO.overview,
            releaseDate = movieDTO.releaseDate,
            title = movieDTO.title,
            voteAverage = movieDTO.voteAverage,
            genres = movieDTO.genres.filter { genre ->
                genre.id in movieDTO.genres.map { it.id }
            }
        )
    }

    override suspend fun getReviews(id: Int): List<ReviewUIModel> {
        return apiService.getReviews(id).reviews.map {
            ReviewUIModel(
                authorDetails = ReviewAuthorItem(
                    name = it.authorDetails.name,
                    username = it.authorDetails.username,
                    avatarPath = it.authorDetails.avatarPath,
                    rating = it.authorDetails.rating ?: DEFAULT_RATING
                ), //TODO: forgot to put newLines
                content = it.content, updateAt = it.updateAt, id = it.id
            )
        }
    }

    companion object {
        private const val DEFAULT_RATING = 0f

        //TODO: rename to PAGE_SIZE
        private const val PAGE_SIZE_20 = 20
    }
}