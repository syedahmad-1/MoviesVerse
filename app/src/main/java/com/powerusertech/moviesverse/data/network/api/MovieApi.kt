package com.powerusertech.moviesverse.data.network.api

import com.powerusertech.moviesverse.data.models.moviedetails.MovieDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("movie/{movie_id}?append_to_response=credits")
    suspend fun getMovieById(@Path("movie_id") movieId:Int):Response<MovieDetailsResponse>

    @GET("tv/{movie_id}?append_to_response=credits")
    suspend fun getTvById(@Path("movie_id") tvId:Int):Response<MovieDetailsResponse>
}