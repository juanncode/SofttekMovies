
package com.github.juanncode.softtekmovies.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.juanncode.domain.usescase.FetchNewMoviesUseCase
import com.github.juanncode.domain.usescase.GetMoviesFlowUseCase
import com.github.juanncode.domain.usescase.IsMovieEmptyUseCase
import com.github.juanncode.domain.usescase.RefreshMoviesUseCase
import com.github.juanncode.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val isMovieEmptyUseCase: IsMovieEmptyUseCase,
    private val refreshMoviesUseCase: RefreshMoviesUseCase,
    private val fetchNewMoviesUseCase: FetchNewMoviesUseCase,
    private val getMoviesFlowUseCase: GetMoviesFlowUseCase,
): ViewModel() {

    var _state =  MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

//    init {
//        getMoviesFlow()
//        validateFetchMovies()
//    }

    fun validateFetchMovies() {
        viewModelScope.launch {
            if (isMovieEmptyUseCase()) {
                fetchMovies()
            }
        }
    }

    fun getMoviesFlow() {
        getMoviesFlowUseCase().onEach { movies ->
            _state.value = _state.value.copy(movies = movies)
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.CleanError -> _state.value = _state.value.copy(error = null)
            HomeEvent.GetNewMovies -> fetchMovies()
            HomeEvent.RefreshMovies -> refreshMovies()
            HomeEvent.InitialValues -> {
                getMoviesFlow()
                validateFetchMovies()
            }
        }
    }

    private fun refreshMovies() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            delay(500)
            val response = refreshMoviesUseCase()
            _state.value = _state.value.copy(loading = false)
            if (response is Resource.Error) {
                _state.value = _state.value.copy(error = response.error)
            }
        }
    }

    fun fetchMovies() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            delay(500)
            val response = fetchNewMoviesUseCase()
            _state.value = _state.value.copy(loading = false)
            if (response is Resource.Error) {
                _state.value = _state.value.copy(error = response.error)
            }
        }
    }


}