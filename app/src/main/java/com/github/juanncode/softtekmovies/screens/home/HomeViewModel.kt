
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
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
            val response = withContext(Dispatchers.IO) {repository.fetchMovies()}
            if (response is Resource.Error) {
                state = state.copy(error = response.error)
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.CleanError -> state = state.copy(error = null)
        }
    }



}