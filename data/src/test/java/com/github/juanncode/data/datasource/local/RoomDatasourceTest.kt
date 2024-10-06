package com.github.juanncode.data.datasource.local

import com.github.juanncode.data.MovieEntityMock
import com.github.juanncode.data.database.MovieDao
import com.github.juanncode.data.database.MovieDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class RoomDatasourceTest {
    @Mock
    private lateinit var movieDao: MovieDao
    @Mock
    private lateinit var movieDatabase: MovieDatabase
    private lateinit var  roomDatasource : RoomDatasource

    @Before
    fun setUp() {
        roomDatasource = RoomDatasource(movieDatabase)
    }

    @Test
    fun `test get movies`() = runTest {
        val movieEntities = listOf(
            MovieEntityMock.create().copy(id = 1, title = "Movie 1", page = 1),
            MovieEntityMock.create().copy(id = 2, title = "Movie 2", page = 1),
        )
        `when`(movieDao.getMoviesByPage()).thenReturn(flowOf(movieEntities))

        `when`(movieDatabase.movieDao).thenReturn(movieDao)

        val movies = roomDatasource.getMovies().first()

        assertEquals(2, movies.size)
        assertEquals("Movie 1", movies[0].title)

        verify(movieDao).getMoviesByPage()
    }

    @Test
    fun testGetLastPageMovie() = runTest {
        `when`(movieDao.getLastPageMovie()).thenReturn(2)

        `when`(movieDatabase.movieDao).thenReturn(movieDao)

        val lastPage = roomDatasource.getLastPageMovie()

        assertEquals(2, lastPage)

        verify(movieDao).getLastPageMovie()
    }

    @Test
    fun testGetMovieById() = runTest {
        val movieEntity = MovieEntityMock.create().copy(1, "Movie 1", page = 1)
        `when`(movieDao.getMovieById(1)).thenReturn(movieEntity)

        `when`(movieDatabase.movieDao).thenReturn(movieDao)

        val movie = roomDatasource.getMovieById(1)

        assertEquals("Movie 1", movie.title)

        verify(movieDao).getMovieById(1)
    }


}