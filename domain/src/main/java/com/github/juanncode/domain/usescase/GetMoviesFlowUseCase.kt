package com.github.juanncode.domain.usescase

import com.github.juanncode.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesFlowUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getMoviesFlow()
}