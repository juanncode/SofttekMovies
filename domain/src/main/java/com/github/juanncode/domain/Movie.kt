package com.github.juanncode.domain

data class Movie(
    val id: Long,
    val title: String,
    val voteAverage: Double,
    val releaseDate: String,
    val overview: String,
    val posterPath: String,
)