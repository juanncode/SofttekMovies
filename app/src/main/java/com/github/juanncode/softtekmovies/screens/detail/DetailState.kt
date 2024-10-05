package com.github.juanncode.softtekmovies.screens.detail

import com.github.juanncode.domain.Movie

data class DetailState(
    val movie: Movie? = null,
    val isLoading: Boolean = false
)