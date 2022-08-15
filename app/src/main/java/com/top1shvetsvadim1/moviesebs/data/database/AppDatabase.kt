package com.top1shvetsvadim1.moviesebs.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

//TODO: better to put export schema to true. Try to do fake (auto)migration to test. Source: https://developer.android.com/training/data-storage/room/migrating-db-versions
@Database(entities = [GenreItemDBModel::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun genreItemDao(): GenreItemDao
}