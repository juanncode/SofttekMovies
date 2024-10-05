package com.github.juanncode.domain.repository

import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMoviesPagingFlow(): Flow<Any>
}