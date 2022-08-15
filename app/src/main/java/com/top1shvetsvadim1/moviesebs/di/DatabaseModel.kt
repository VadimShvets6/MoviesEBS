package com.top1shvetsvadim1.moviesebs.di

import android.content.Context
import androidx.room.Room
import com.top1shvetsvadim1.moviesebs.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModel {
    //TODO: unnecessary newLine

    //TODO: bad database name
    private const val DB_NAME = "app_database.db"

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DB_NAME
    )
        //TODO: destructive migration is not a good solution when you store user's data in Room. Try to test autoMigrations.
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideGenreListDao(db: AppDatabase) = db.genreItemDao()
}