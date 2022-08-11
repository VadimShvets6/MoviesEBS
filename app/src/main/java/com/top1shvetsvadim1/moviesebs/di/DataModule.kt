package com.top1shvetsvadim1.moviesebs.di

import com.top1shvetsvadim1.moviesebs.data.database.GenreItemDao
import com.top1shvetsvadim1.moviesebs.data.database.Mapper
import com.top1shvetsvadim1.moviesebs.data.repository.MovieRepositoryImpl
import com.top1shvetsvadim1.moviesebs.di.NetworkModule.Companion.apiService
import com.top1shvetsvadim1.moviesebs.domain.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideMovieRepository(genreItemDao: GenreItemDao, mapper: Mapper): MovieRepository {
        return MovieRepositoryImpl(apiService, genreItemDao, mapper)
    }
}