package com.powerusertech.moviesverse.data.network

import com.powerusertech.moviesverse.data.models.TrendingResponse
import com.powerusertech.moviesverse.data.network.Api.TrendingApi
import retrofit2.Response
import javax.inject.Inject

class TrendingRemoteDataSource @Inject constructor(private val trendingApi: TrendingApi) {
    suspend fun getTrendingMovies(): Response<TrendingResponse> {
        return trendingApi.getTrendingMoviesByWeek()
    }
    suspend fun getAllTrendingByWeek():Response<TrendingResponse>{
        return trendingApi.getAllTrendingThisWeek()
    }

    suspend fun getTrendingTvByWeek():Response<TrendingResponse>{
        return trendingApi.getTrendingTvThisWeek()
    }
}