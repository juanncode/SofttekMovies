package com.github.juanncode.data.datasource.local

import com.github.juanncode.data.datasource.remote.model.MovieRemote
import com.github.juanncode.domain.Movie
import kotlinx.coroutines.flow.Flow

interface LocalDatasource {
    fun getMovies(): Flow<List<Movie>>
    suspend fun saveMovies(movies: List<MovieRemote>, isRefreshing: Boolean, page: Int = 1)
    suspend fun getLastPageMovie(): Int?

}