package com.github.juanncode.data.repository

import com.github.juanncode.data.MovieEntityMock
import com.github.juanncode.data.MovieRemoteMock
import com.github.juanncode.data.database.MovieEntity
import com.github.juanncode.data.datasource.local.LocalDatasource
import com.github.juanncode.data.datasource.remote.RemoteDataSource
import com.github.juanncode.data.mappers.toDomain
import com.github.juanncode.domain.utils.ErrorGeneric
import com.github.juanncode.domain.utils.Resource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyBoolean
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.anyList
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryImplTest {

    @Mock private lateinit var localDatasource: LocalDatasource
    @Mock private lateinit var remoteDatasource: RemoteDataSource
    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setup() {
        repository = MovieRepositoryImpl(localDatasource, remoteDatasource)
    }

    @Test
    fun `fetchMovies success`() = runTest {
        val movies = listOf(MovieRemoteMock.create().copy(id = 1, title = "Movie 1"))
        `when`(localDatasource.getLastPageMovie()).thenReturn(1)
        `when`(remoteDatasource.getMovies(2)).thenReturn(Resource.Success(movies))

        val result = repository.fetchMovies()

        assertTrue(result is Resource.Success)
        verify(localDatasource).saveMovies(movies, isRefreshing = false, page = 2)

    }

    @Test
    fun `fetchMovies error`() = runTest {
        val error = ErrorGeneric(code = 404, userMessage = "Not Found", error = "error details")
        `when`(localDatasource.getLastPageMovie()).thenReturn(1)
        `when`(remoteDatasource.getMovies(2)).thenReturn(Resource.Error(error))

        val result = repository.fetchMovies()

        assertTrue(result is Resource.Error)
        assertEquals(error, (result as Resource.Error).error)
        verify(localDatasource, never()).saveMovies(anyList(), anyBoolean(), anyInt())
    }

    @Test
    fun `refreshMovies success`() = runTest {
        val movies = listOf(MovieRemoteMock.create().copy(id = 1, title = "Movie 1"))
        `when`(remoteDatasource.getMovies(1)).thenReturn(Resource.Success(movies))

        val result = repository.refreshMovies()

        assertTrue(result is Resource.Success)
        verify(localDatasource).saveMovies(movies, isRefreshing = true)
    }

    @Test
    fun `refreshMovies error`() = runTest {
        val error = ErrorGeneric(code = 500, userMessage = "Server Error", error = "details")
        `when`(remoteDatasource.getMovies(1)).thenReturn(Resource.Error(error))

        val result = repository.refreshMovies()

        assertTrue(result is Resource.Error)
        assertEquals(error, (result as Resource.Error).error)
        verify(localDatasource, never()).saveMovies(anyList(), anyBoolean(), anyInt())
    }

    @Test
    fun `getMoviesFlow success`() = runTest {
        val movies = listOf(MovieEntityMock.create().copy(id = 1, title = "Movie 1").toDomain())
        `when`(localDatasource.getMovies()).thenReturn(flowOf(movies))

        val result = repository.getMoviesFlow()

        result.collect { moviesList ->
            assertEquals(movies, moviesList)
        }
    }

    @Test
    fun `isMoviesEmpty returns true`() = runTest {
        `when`(localDatasource.getLastPageMovie()).thenReturn(null)

        val result = repository.isMoviesEmpty()

        assertTrue(result)
    }

    @Test
    fun `isMoviesEmpty returns false`() = runTest {
        `when`(localDatasource.getLastPageMovie()).thenReturn(1)

        val result = repository.isMoviesEmpty()

        assertFalse(result)
    }

    @Test
    fun `get movie by id`() = runTest {
        val movie = MovieEntityMock.create().copy(id = 1, title = "Movie 1").toDomain()

        `when`(localDatasource.getMovieById(1)).thenReturn(movie)

        val response = repository.getMovie(1)

        assertEquals(movie, response)

    }
}