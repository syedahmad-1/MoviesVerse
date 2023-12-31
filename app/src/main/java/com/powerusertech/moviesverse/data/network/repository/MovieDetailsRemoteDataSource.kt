package com.powerusertech.moviesverse.data.network.repository

import com.powerusertech.moviesverse.data.models.moviedetails.MovieDetailsResponse
import com.powerusertech.moviesverse.data.network.api.MovieApi
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Response
import javax.inject.Inject


@ViewModelScoped
class MovieDetailsRemoteDataSource @Inject constructor(private val movieApi: MovieApi) {
    suspend fun getMovieById(movieId:Int): Response<MovieDetailsResponse> {
        return movieApi.getMovieById(movieId)
    }

}