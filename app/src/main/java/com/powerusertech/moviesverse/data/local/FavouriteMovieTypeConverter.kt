package com.powerusertech.moviesverse.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.powerusertech.moviesverse.data.models.moviedetails.MovieDetailsResponse

class FavouriteMovieTypeConverter {
    var gson = Gson()
    @TypeConverter
    fun favouriteMoveToString(movieDetails: MovieDetailsResponse): String {
        return gson.toJson(movieDetails)
    }

    @TypeConverter
    fun stringToFavouriteMovie(data: String): MovieDetailsResponse {

        val listType = object : TypeToken<MovieDetailsResponse>() {}.type
        return gson.fromJson(data, listType)
    }
}