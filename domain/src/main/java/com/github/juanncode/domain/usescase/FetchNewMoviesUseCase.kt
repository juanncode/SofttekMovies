package com.github.juanncode.domain.usescase

import com.github.juanncode.domain.repository.MovieRepository
import com.github.juanncode.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchNewMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke() : Resource<Unit>{
        return withContext(Dispatchers.IO) {
            repository.fetchMovies()
        }
    }


}