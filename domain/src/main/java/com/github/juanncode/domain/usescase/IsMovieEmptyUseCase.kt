package com.github.juanncode.domain.usescase

import com.github.juanncode.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IsMovieEmptyUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): Boolean {
        return withContext(Dispatchers.IO) {repository.isMoviesEmpty()}
    }

}