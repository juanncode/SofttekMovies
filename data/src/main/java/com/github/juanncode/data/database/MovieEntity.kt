package com.github.juanncode.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val voteAverage: Double,
    val releaseDate: String,
    val overview: String,
    val posterPath: String,
)
