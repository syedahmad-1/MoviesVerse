package com.powerusertech.moviesverse.data

import com.powerusertech.moviesverse.data.models.Result
import com.powerusertech.moviesverse.data.models.TrendingMovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface MoviesApi {

    @GET("")
    suspend fun getMoviesById(id: Int): Response<Result>

    @GET("trending/movie/week")
    suspend fun getTrendingMoviewByWeek(): Response<TrendingMovieResponse>
}