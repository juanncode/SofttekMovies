package com.github.juanncode.data.di

import com.github.juanncode.data.datasource.local.LocalDatasource
import com.github.juanncode.data.datasource.local.RoomDatasource
import com.github.juanncode.data.repository.MovieRepositoryImpl
import com.github.juanncode.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindRoomDataSource(localDataSource: RoomDatasource) : LocalDatasource

    @Binds
    abstract fun bindRepository(repository: MovieRepositoryImpl) : MovieRepository

}