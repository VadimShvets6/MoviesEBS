package com.top1shvetsvadim1.moviesebs.di

import com.top1shvetsvadim1.moviesebs.data.repository.MovieRepositoryImpl
import com.top1shvetsvadim1.moviesebs.domain.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindRepo(impl: MovieRepositoryImpl): MovieRepository
}