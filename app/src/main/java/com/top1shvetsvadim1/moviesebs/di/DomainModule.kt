package com.top1shvetsvadim1.moviesebs.di

import com.top1shvetsvadim1.moviesebs.domain.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetMoviesListUseCase(movieRepository: MovieRepository): GetMoviesListUseCase {
        return GetMoviesListUseCase(movieRepository)
    }

    @Provides
    fun provideGetGenreListUseCase(movieRepository: MovieRepository): GetGenreListUseCase {
        return GetGenreListUseCase(movieRepository)
    }

    @Provides
    fun provideGetReviewListUseCase(movieRepository: MovieRepository): GetReviewsUseCase {
        return GetReviewsUseCase(movieRepository)
    }

    @Provides
    fun provideGetMovieDetailUseCase(movieRepository: MovieRepository): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(movieRepository)
    }

    @Provides
    fun provideSearchMovieUseCase(movieRepository: MovieRepository): SearchMovieUseCase {
        return SearchMovieUseCase(movieRepository)
    }

}