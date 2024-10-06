package com.github.juanncode.data.mappers

import com.github.juanncode.data.MovieEntityMock
import com.github.juanncode.data.MovieRemoteMock
import com.github.juanncode.data.datasource.remote.model.MovieRemote
import org.junit.Assert.*
import org.junit.Test

class MappersTest {
    @Test
    fun `test MovieRemote toDatabase mapping`() {
        val movieRemote = MovieRemoteMock.create().copy(
            id = 1,
            title = "Movie Title",
            voteAverage = 8.5,
            releaseDate = "2022-09-15",
            overview = "This is an overview of the movie.",
            posterPath = "/poster_path.jpg",
        )
        val page = 2

        val movieEntity = movieRemote.toDatabase(page)

        assertEquals(1, movieEntity.id)
        assertEquals("Movie Title", movieEntity.title)
        assertEquals(8.5, movieEntity.voteAverage, 0.0)
        assertEquals("2022-09-15", movieEntity.releaseDate)
        assertEquals("This is an overview of the movie.", movieEntity.overview)
        assertEquals("https://image.tmdb.org/t/p/w500/poster_path.jpg", movieEntity.posterPath)
        assertEquals(2, movieEntity.page)
    }

    @Test
    fun `test MovieEntity toDomain mapping`() {
        val movieEntity = MovieEntityMock.create().copy(
            id = 1,
            title = "Movie Title",
            voteAverage = 8.5,
            releaseDate = "2022-09-15",
            overview = "This is an overview of the movie.",
            posterPath = "https://image.tmdb.org/t/p/w500/poster_path.jpg",
            page = 2
        )

        val movie = movieEntity.toDomain()

        assertEquals(1, movie.id)
        assertEquals("Movie Title", movie.title)
        assertEquals(8.5, movie.voteAverage, 0.0)
        assertEquals("2022-09-15", movie.releaseDate)
        assertEquals("This is an overview of the movie.", movie.overview)
        assertEquals("https://image.tmdb.org/t/p/w500/poster_path.jpg", movie.posterPath)
        assertEquals(2, movie.page)
    }
}