package com.github.juanncode.softtekmovies.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.juanncode.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
): ViewModel() {
    var state by mutableStateOf(DetailState())
        private set

    private fun getMovie(id: Long) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val movie = withContext(Dispatchers.IO) {movieRepository.getMovie(id)}
            state = state.copy(isLoading = false, movie = movie)
        }

    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.GetMovie -> getMovie(event.id)
        }

    }


}