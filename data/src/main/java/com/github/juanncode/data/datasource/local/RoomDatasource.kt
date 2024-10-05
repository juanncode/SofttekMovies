package com.github.juanncode.data.datasource.local

import androidx.room.withTransaction
import com.github.juanncode.data.database.MovieDatabase
import com.github.juanncode.data.datasource.remote.model.MovieRemote
import com.github.juanncode.data.mappers.toDatabase
import com.github.juanncode.data.mappers.toDomain
import com.github.juanncode.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomDatasource @Inject constructor(
    private val movieDatabase: MovieDatabase
) : LocalDatasource {
    override fun getMovies(): Flow<List<Movie>> {
        return movieDatabase.movieDao.getMoviesByPage().map { it.map { it.toDomain() } }
    }

    override suspend fun saveMovies(movies: List<MovieRemote>, isRefreshing: Boolean, page: Int) {
        movieDatabase.withTransaction {
            if(isRefreshing) {
                movieDatabase.movieDao.clearAll()
            }
            movieDatabase.movieDao.upsertAll(movies.map { it.toDatabase(page) })
        }
    }

    override suspend fun getLastPageMovie(): Int? {
        return movieDatabase.movieDao.getLastPageMovie()
    }


}