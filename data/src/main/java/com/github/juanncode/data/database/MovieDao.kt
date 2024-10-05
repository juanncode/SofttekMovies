package com.github.juanncode.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Upsert
    suspend fun upsertAll(beers: List<MovieEntity>)

    @Query("SELECT * FROM movieentity")
    fun getMoviesByPage(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movieentity where id = :id")
    fun getMovieById(id: Long): MovieEntity

    @Query("SELECT MAX(page) FROM movieentity")
    fun getLastPageMovie(): Int?

    @Query("DELETE FROM movieentity")
    suspend fun clearAll()
}