package com.top1shvetsvadim1.moviesebs.data.repository

import com.top1shvetsvadim1.moviesebs.data.database.GenreItemDBModel
import com.top1shvetsvadim1.moviesebs.data.database.GenreItemDao
import com.top1shvetsvadim1.moviesebs.data.database.Mapper
import com.top1shvetsvadim1.moviesebs.data.network.ApiService
import com.top1shvetsvadim1.moviesebs.domain.MovieEntity
import com.top1shvetsvadim1.moviesebs.domain.MovieRepository
import com.top1shvetsvadim1.moviesebs.domain.ReviewAuthorItem
import com.top1shvetsvadim1.moviesebs.domain.ReviewUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val genreItemDao: GenreItemDao,
    private val mapper: Mapper
) : MovieRepository {

    override suspend fun getMoviesList(): Flow<List<MovieEntity>> {
        return combine(
            flowOf(apiService.getMoviesList(page = 1).movies),
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
                    rating = it.authorDetails.rating ?: 0f
                ), content = it.content, updateAt = it.updateAt, id = it.id
            )
        }
    }

    override suspend fun searchMovie(name: String): Flow<List<MovieEntity>> {
        return combine(
            flowOf(apiService.getSearchMovieList(searchName = name).movies),
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
}