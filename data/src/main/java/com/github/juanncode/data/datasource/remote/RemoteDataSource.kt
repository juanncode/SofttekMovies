package com.github.juanncode.data.datasource.remote

import com.github.juanncode.data.datasource.remote.model.MovieRemote
import com.github.juanncode.domain.utils.Resource

interface RemoteDataSource {
    suspend fun getMovies(page: Int): Resource<List<MovieRemote>>
}