package com.github.juanncode.softtekmovies.screens

import com.github.juanncode.domain.Movie

class MovieMock {
    companion object {
        fun create() = Movie(
            id = 1,
            title = "Movie 1",
            page = 1,
            posterPath = "poster.jpg",
            voteAverage = 23.2,
            releaseDate = "12-09-2022",
            overview = "This is an overview"
        )
    }
}