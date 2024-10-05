package com.github.juanncode.data.database

import androidx.paging.PagingSource
import androidx.room.Query
import androidx.room.Upsert

interface MovieDao {
    @Upsert
    suspend fun upsertAll(beers: List<MovieEntity>)

    @Query("SELECT * FROM movieentity where page = :page")
    fun getMoviesByPage(page: Int): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movieentity")
    suspend fun clearAll()
}