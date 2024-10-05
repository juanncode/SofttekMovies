package com.github.juanncode.data.datasource.local

import androidx.paging.LoadType
import androidx.paging.PagingSource
import com.github.juanncode.data.database.MovieEntity

interface LocalDatasource {
    fun getMovies(): PagingSource<Int, MovieEntity>
    suspend fun saveMovies(movies: List<MovieEntity>, loadType: LoadType)
}