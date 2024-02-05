package com.powerusertech.moviesverse.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.powerusertech.moviesverse.core.utils.Constants.FAV_MOVIE_TABLE
import com.powerusertech.moviesverse.data.models.moviedetails.MovieDetailsResponse


@Entity(tableName = FAV_MOVIE_TABLE)
class FavouriteMovieEntity(
    var movieDetailsResponse: MovieDetailsResponse
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = movieDetailsResponse.id

    @ColumnInfo(name = "title")
    var title:String = movieDetailsResponse.title

}