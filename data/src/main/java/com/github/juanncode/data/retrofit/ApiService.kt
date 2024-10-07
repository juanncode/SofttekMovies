package com.github.juanncode.data.retrofit

import com.github.juanncode.data.datasource.remote.model.MoviesAPIResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/upcoming")
    suspend fun fetchMovies(@Query("page") page: Int): MoviesAPIResult
}