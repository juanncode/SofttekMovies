package com.github.juanncode.softtekmovies.screens.home

import com.github.juanncode.domain.Movie
import com.github.juanncode.domain.utils.ErrorGeneric

data class HomeState(
    val loading: Boolean = false,
    val error: ErrorGeneric? = null,
    val movies: List<Movie> = emptyList(),
)