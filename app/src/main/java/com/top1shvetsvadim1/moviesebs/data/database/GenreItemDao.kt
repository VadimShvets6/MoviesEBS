package com.top1shvetsvadim1.moviesebs.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreItemDao {

    @Query("SELECT * FROM genres")
    fun getGenreList(): Flow<List<GenreItemDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGenreList(genreListDBModel: List<GenreItemDBModel>)
}