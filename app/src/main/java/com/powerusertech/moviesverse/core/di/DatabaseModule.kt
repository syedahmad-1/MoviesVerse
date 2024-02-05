package com.powerusertech.moviesverse.core.di

import android.content.Context
import androidx.room.Room
import com.powerusertech.moviesverse.core.utils.Constants.DATABASE_NAME
import com.powerusertech.moviesverse.data.local.FavouriteMovieDao
import com.powerusertech.moviesverse.data.local.FavouriteMovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFavouriteMovieDatabase(@ApplicationContext context: Context): FavouriteMovieDatabase {
        return Room.databaseBuilder(
            context,
            FavouriteMovieDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideFavouriteMovieDao(database: FavouriteMovieDatabase): FavouriteMovieDao {
        return database.favoriteMovieDao()
    }
}
