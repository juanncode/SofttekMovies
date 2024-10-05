@file:OptIn(ExperimentalPagingApi::class)

package com.github.juanncode.data.datasource.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.github.juanncode.data.database.MovieDatabase
import com.github.juanncode.data.database.MovieEntity
import com.github.juanncode.data.datasource.local.LocalDatasource
import com.github.juanncode.data.mappers.toDatabase
import com.github.juanncode.data.retrofit.ApiService
import com.github.juanncode.data.util.toError
import javax.inject.Inject

class MovieRemoteMediator @Inject constructor(
    private val apiService: ApiService,
    private val localDatasource: LocalDatasource
): RemoteMediator<Int, MovieEntity>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, MovieEntity>): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.page?.plus(1) ?: 1
                }
            }

            val response = apiService.fetchMovies(page = loadKey)

            val moviesDb = response.results.map {
                it.toDatabase(page = loadKey)
            }

            localDatasource.saveMovies(moviesDb, LoadType.REFRESH)

            MediatorResult.Success(
                endOfPaginationReached = response.results.isEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(e.toError())
        }
    }
}