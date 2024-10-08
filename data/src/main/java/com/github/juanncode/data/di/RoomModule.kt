package com.github.juanncode.data.di

import android.content.Context
import androidx.room.Room
import com.github.juanncode.data.database.MovieDao
import com.github.juanncode.data.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(pokemonDatabase: MovieDatabase) : MovieDao{
        return pokemonDatabase.movieDao
    }
}