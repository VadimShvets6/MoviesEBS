package com.top1shvetsvadim1.moviesebs.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenreItemDBModel(
    @PrimaryKey val id: Int,
    val name: String
)