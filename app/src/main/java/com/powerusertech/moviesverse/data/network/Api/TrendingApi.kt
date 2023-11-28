package com.powerusertech.moviesverse.data.network.Api

import com.powerusertech.moviesverse.data.models.TrendingResponse
import retrofit2.Response
import retrofit2.http.GET

interface TrendingApi {

    @GET("trending/movie/week")
    suspend fun getTrendingMoviesByWeek(): Response<TrendingResponse>

    @GET("trending/all/week")
    suspend fun getAllTrendingThisWeek(): Response<TrendingResponse>

    @GET("trending/tv/week")
    suspend fun getTrendingTvThisWeek(): Response<TrendingResponse>
}