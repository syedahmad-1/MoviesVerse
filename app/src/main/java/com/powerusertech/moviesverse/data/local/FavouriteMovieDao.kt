package com.powerusertech.moviesverse.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.powerusertech.moviesverse.data.models.moviedetails.MovieDetailsResponse
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieEntity: FavouriteMovieEntity)
    @Query("SELECT * FROM favourite_movie_table ORDER BY id ASC")
    fun getAllFavorites(): Flow<List<FavouriteMovieEntity>>

    @Query("DELETE FROM favourite_movie_table WHERE id = :movieId ")
    suspend fun deleteById(movieId:Int)

    @Query("SELECT * FROM favourite_movie_table WHERE title LIKE '%' || :title || '%'")
    fun searchMovie(title:String):List<FavouriteMovieEntity>
}