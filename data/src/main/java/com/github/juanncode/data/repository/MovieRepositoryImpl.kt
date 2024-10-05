@file:OptIn(ExperimentalPagingApi::class)

package com.github.juanncode.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.github.juanncode.data.database.MovieDao
import com.github.juanncode.data.datasource.local.LocalDatasource
import com.github.juanncode.data.datasource.remote.MovieRemoteMediator
import com.github.juanncode.data.mappers.toDomain
import com.github.juanncode.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val localDatasource: LocalDatasource,
    private val movieRemoteMediator: MovieRemoteMediator
): MovieRepository {
    override fun getMoviesPagingFlow(): Flow<Any> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                enablePlaceholders = true
            ),
            remoteMediator = movieRemoteMediator,
            pagingSourceFactory = {
                localDatasource.getMovies()
            }

        ).flow.map {
            it.map {  movieEntity ->
                movieEntity.toDomain()
            }
        }

        return pager
    }
}