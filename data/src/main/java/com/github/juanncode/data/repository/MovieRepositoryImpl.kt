
package com.github.juanncode.data.repository

import com.github.juanncode.data.datasource.local.LocalDatasource
import com.github.juanncode.data.datasource.remote.RemoteDataSource
import com.github.juanncode.domain.Movie
import com.github.juanncode.domain.repository.MovieRepository
import com.github.juanncode.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val localDatasource: LocalDatasource,
    private val remoteDatasource: RemoteDataSource
): MovieRepository {
    override fun getMoviesFlow(): Flow<List<Movie>> {
        return localDatasource.getMovies()
    }

    override suspend fun fetchMovies(): Resource<Unit> {
        var lastPage = localDatasource.getLastPageMovie()
        if (lastPage == null) lastPage = 1 else lastPage += 1
        val response = remoteDatasource.getMovies(lastPage)
        return when(response) {
            is Resource.Error -> Resource.Error(response.error)
            is Resource.Success -> {
                localDatasource.saveMovies(response.value, isRefreshing = false, lastPage)
                Resource.Success(Unit)
            }
        }
    }

    override suspend fun refreshMovies(): Resource<Unit> {
        val response = remoteDatasource.getMovies(1)
        return when(response) {
            is Resource.Error -> Resource.Error(response.error)
            is Resource.Success -> {
                localDatasource.saveMovies(response.value, isRefreshing = true)
                Resource.Success(Unit)
            }
        }
    }

    override suspend fun isMoviesEmpty(): Boolean {
        return localDatasource.getLastPageMovie() == null
    }

    override suspend fun getMovie(id: Long): Movie {
        return localDatasource.getMovieById(id)
    }
}