package com.top1shvetsvadim1.moviesebs.data.network

import com.top1shvetsvadim1.moviesebs.domain.GenreList
import com.top1shvetsvadim1.moviesebs.domain.MovieDetailDTO
import com.top1shvetsvadim1.moviesebs.domain.MoviesItemsList
import com.top1shvetsvadim1.moviesebs.domain.ReviewItemList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    suspend fun getMoviesList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = PARAM_API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String = PARAM_LANGUAGE,
        @Query(QUERY_PARAM_SORT_BY) sortedBy: String = PARAM_SORT_BY,
        @Query(QUERY_PARAM_PAGE) page: Int
    ): MoviesItemsList

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = PARAM_API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String = PARAM_LANGUAGE
    ): MovieDetailDTO

    @GET("genre/movie/list")
    suspend fun getGenreList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = PARAM_API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String = PARAM_LANGUAGE
    ): GenreList

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") id: Int,
        @Query(QUERY_PARAM_API_KEY) apikey: String = PARAM_API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String = PARAM_LANGUAGE
    ): ReviewItemList

    @GET("search/movie")
    suspend fun getSearchMovieList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = PARAM_API_KEY,
        @Query(QUERY_SEARCH) searchName: String,
        @Query(QUERY_PARAM_PAGE) page : Int
    ): MoviesItemsList

    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LANGUAGE = "language"
        private const val QUERY_PARAM_SORT_BY = "sort_by"
        private const val QUERY_PARAM_PAGE = "page"
        private const val QUERY_SEARCH = "query"

        private const val PARAM_API_KEY = "a43ee367646362d37a2d063cef6dffd9"
        private const val PARAM_LANGUAGE = "en-US"
        private const val PARAM_SORT_BY = "popularity.desc"
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}