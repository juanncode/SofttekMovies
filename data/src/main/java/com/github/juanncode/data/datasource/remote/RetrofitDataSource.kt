package com.github.juanncode.data.datasource.remote

import com.github.juanncode.data.datasource.remote.model.MovieRemote
import com.github.juanncode.data.retrofit.ApiService
import com.github.juanncode.data.util.safeApiCall
import com.github.juanncode.domain.utils.Resource
import javax.inject.Inject

class RetrofitDataSource @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource{
    override suspend fun fetchMovies(page: Int): Resource<List<MovieRemote>> {
        return safeApiCall {
            apiService.fetchMovies(page).results
        }
    }
}