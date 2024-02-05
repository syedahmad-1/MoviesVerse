package com.powerusertech.moviesverse.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [FavouriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(FavouriteMovieTypeConverter::class)
abstract class FavouriteMovieDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavouriteMovieDao
}