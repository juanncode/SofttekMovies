package com.github.juanncode.data.datasource.local

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.github.juanncode.data.database.MovieDatabase
import com.github.juanncode.data.database.MovieEntity
import javax.inject.Inject

class RoomDatasource @Inject constructor(
    private val movieDatabase: MovieDatabase
) : LocalDatasource {
    override suspend fun getMovies(): PagingSource<Int, MovieEntity> {
        return movieDatabase.movieDao.getMoviesByPage()
    }

    override suspend fun saveMovies(movies: List<MovieEntity>, loadType: LoadType) {
        movieDatabase.withTransaction {
            if(loadType == LoadType.REFRESH) {
                movieDatabase.movieDao.clearAll()
            }
            movieDatabase.movieDao.upsertAll(movies)
        }
    }
}