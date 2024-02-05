package com.powerusertech.moviesverse.data.network.repository

import com.powerusertech.moviesverse.data.local.FavouriteMovieDao
import com.powerusertech.moviesverse.data.local.FavouriteMovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailsLocalDataSource @Inject constructor(private val favouriteMovieDao: FavouriteMovieDao) {
    suspend fun addFavoriteMovies(movieEntity: FavouriteMovieEntity){
        favouriteMovieDao.insertMovie(movieEntity)
    }

    fun getAllMovies(): Flow<List<FavouriteMovieEntity>> {
        return favouriteMovieDao.getAllFavorites()
    }
}
