package com.top1shvetsvadim1.moviesebs.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GenreItemDBModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun genreItemDao(): GenreItemDao
}