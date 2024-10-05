package com.github.juanncode.data.mappers

import com.github.juanncode.data.database.MovieEntity
import com.github.juanncode.data.datasource.remote.model.MovieRemote
import com.github.juanncode.domain.Movie

fun MovieRemote.toDatabase(page: Int): MovieEntity = MovieEntity(
    id,
    title,
    voteAverage,
    releaseDate,
    overview,
    posterPath,
    page = page
)

fun MovieEntity.toDomain() = Movie(
    id, title, voteAverage, releaseDate, overview, posterPath, page
)