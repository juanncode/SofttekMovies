package com.github.juanncode.data.di

import com.github.juanncode.data.EmailPatterValidator
import com.github.juanncode.data.datasource.local.LocalDatasource
import com.github.juanncode.data.datasource.local.RoomDatasource
import com.github.juanncode.data.datasource.remote.RemoteDataSource
import com.github.juanncode.data.datasource.remote.RetrofitDataSource
import com.github.juanncode.data.repository.MovieRepositoryImpl
import com.github.juanncode.domain.PatternValidator
import com.github.juanncode.domain.UserDataValidator
import com.github.juanncode.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindRoomDataSource(localDataSource: RoomDatasource) : LocalDatasource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: RetrofitDataSource) : RemoteDataSource

    @Binds
    abstract fun bindRepository(repository: MovieRepositoryImpl) : MovieRepository

    @Binds
    abstract fun patterValidator(patternValidator: EmailPatterValidator) : PatternValidator

}

@Module
@InstallIn(SingletonComponent::class)
class UserModule {
    @Provides
    @Singleton
    fun provideUserDataValidator(patternValidator: PatternValidator): UserDataValidator {
        return UserDataValidator(patternValidator)
    }
}