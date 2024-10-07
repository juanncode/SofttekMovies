@file:OptIn(ExperimentalCoroutinesApi::class)

package com.github.juanncode.softtekmovies.screens.home

import app.cash.turbine.test
import com.github.juanncode.domain.usescase.FetchNewMoviesUseCase
import com.github.juanncode.domain.usescase.GetMoviesFlowUseCase
import com.github.juanncode.domain.usescase.IsMovieEmptyUseCase
import com.github.juanncode.domain.usescase.RefreshMoviesUseCase
import com.github.juanncode.domain.utils.ErrorGeneric
import com.github.juanncode.domain.utils.Resource
import com.github.juanncode.softtekmovies.screens.CoroutinesTestRule
import com.github.juanncode.softtekmovies.screens.MovieMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    @Mock lateinit var getMoviesFlowUseCase: GetMoviesFlowUseCase
    @Mock lateinit var refreshMoviesUseCase: RefreshMoviesUseCase
    @Mock lateinit var isMoviesEmptyUseCase: IsMovieEmptyUseCase
    @Mock lateinit var fetchNewMoviesUseCase: FetchNewMoviesUseCase

    @get:Rule
    val rule = CoroutinesTestRule()

    @Before
    fun setup() {
        viewModel = HomeViewModel(
            isMovieEmptyUseCase = isMoviesEmptyUseCase,
            refreshMoviesUseCase,
            fetchNewMoviesUseCase,
            getMoviesFlowUseCase,
        )
    }

    @Test
    fun `should update state when getMoviesFlow is called`() = runTest {
        val movies = listOf(MovieMock.create().copy(id = 1, title = "Movie 1"))
        whenever(getMoviesFlowUseCase()).thenReturn(flowOf(movies))

        viewModel.getMoviesFlow()

        viewModel.state.test {
            awaitItem()
            val emission = awaitItem()
            assertEquals(movies,emission.movies)
        }
    }

    @Test
    fun `should handle HomeEvent InitialValues`() = runTest {
        // Arrange
        val movies = listOf(MovieMock.create().copy(id = 1, title = "Movie 1"))
        whenever(isMoviesEmptyUseCase()).thenReturn(false)
        whenever(getMoviesFlowUseCase()).thenReturn(flowOf(movies))

        // Act
        viewModel.onEvent(HomeEvent.InitialValues)

        // Assert
        viewModel.state.test {
            awaitItem()
            val emission = awaitItem()
            assertEquals(movies,emission.movies)
        }
        verify(getMoviesFlowUseCase, times(1)).invoke()
    }

    @Test
    fun `should set loading and fetch movies on event GetNewMovies`() = runTest {
        whenever(fetchNewMoviesUseCase()).thenReturn(Resource.Success(Unit))

        viewModel.onEvent(HomeEvent.GetNewMovies)

        viewModel.state.test {
            awaitItem()
            val initialEmission = awaitItem()
            assertTrue(initialEmission.loading)


            val finalEmission = awaitItem()
            assertFalse(finalEmission.loading)
        }
    }

    @Test
    fun `should handle error state when fetchMovies returns Resource Error`() = runTest {
        val errorGeneric =  ErrorGeneric(
            code = 0,
            userMessage = "Tuvimos un problema de conectividad, por favor revise su conexión a internet y vuelva a intentarlo",
            error = "Network error"
        )
        whenever(fetchNewMoviesUseCase()).thenReturn(Resource.Error(errorGeneric))

        viewModel.fetchMovies()

        viewModel.state.test {
            awaitItem()
            val initialEmission = awaitItem()
            assertTrue(initialEmission.loading)


            val finalEmission = awaitItem()
            assertFalse(finalEmission.loading)
            assertEquals(errorGeneric,awaitItem().error)
        }
    }

    @Test
    fun `refreshMovies sets loading and no error on successful fetch`() = runTest {
        whenever( refreshMoviesUseCase()).thenReturn( Resource.Success(Unit))

        viewModel.onEvent(HomeEvent.RefreshMovies)

        viewModel.state.test {
            awaitItem()
            assertEquals(HomeState(loading = true), awaitItem())
            assertEquals(HomeState(loading = false, error = null), awaitItem())
        }
    }

}