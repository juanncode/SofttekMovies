package com.github.juanncode.domain.repository

import com.github.juanncode.domain.Movie
import com.github.juanncode.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMoviesFlow(): Flow<List<Movie>>
    suspend fun fetchMovies(): Resource<Unit>
    suspend fun refreshMovies(): Resource<Unit>
}