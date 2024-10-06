package com.github.juanncode.data

import com.github.juanncode.data.database.MovieEntity

class MovieEntityMock {
    companion object {
        fun create() = MovieEntity(
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