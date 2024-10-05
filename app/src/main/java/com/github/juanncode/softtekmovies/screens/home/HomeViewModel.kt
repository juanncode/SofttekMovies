
package com.github.juanncode.softtekmovies.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.juanncode.domain.repository.MovieRepository
import com.github.juanncode.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {

        repository.getMoviesFlow().onEach { movies ->
            state = state.copy(movies = movies)
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            if (withContext(Dispatchers.IO){repository.isMoviesEmpty()}) {
                fetchMovies()
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.CleanError -> state = state.copy(error = null)
            HomeEvent.GetNewMovies -> fetchMovies()
            HomeEvent.RefreshMovies -> refreshMovies()
        }
    }

    private fun refreshMovies() {
        viewModelScope.launch {
            state = state.copy(loading = true)
            delay(500)
            val response = withContext(Dispatchers.IO) {repository.refreshMovies()}
            state = state.copy(loading = false)
            if (response is Resource.Error) {
                state = state.copy(error = response.error)
            }
        }
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            state = state.copy(loading = true)
            delay(500)
            val response = withContext(Dispatchers.IO) {repository.fetchMovies()}
            state = state.copy(loading = false)
            if (response is Resource.Error) {
                state = state.copy(error = response.error)
            }
        }
    }


}