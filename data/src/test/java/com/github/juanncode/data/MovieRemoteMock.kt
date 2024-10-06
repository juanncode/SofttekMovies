package com.github.juanncode.data

import com.github.juanncode.data.datasource.remote.model.MovieRemote

class MovieRemoteMock {
    companion object {
        fun create() = MovieRemote(
            id = 1,
            title = "Movie 1",
            posterPath = "poster.jpg",
            voteAverage = 23.2,
            releaseDate = "12-09-2022",
            overview = "This is an overview",
            adult = false,
            video = false,
            genreIDS = listOf(),
            backdropPath = "",
            voteCount = 2,
            originalTitle = "",
            popularity = 2.2
        )
    }
}