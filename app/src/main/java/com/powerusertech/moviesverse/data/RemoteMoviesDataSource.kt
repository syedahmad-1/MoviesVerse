package com.powerusertech.moviesverse.data

import com.powerusertech.moviesverse.data.models.TrendingMovieResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteMoviesDataSource @Inject constructor(private val moviesApi: MoviesApi) {
    suspend fun getTrendingMovies(): Response<TrendingMovieResponse> {
        return moviesApi.getTrendingMoviewByWeek()
    }
}