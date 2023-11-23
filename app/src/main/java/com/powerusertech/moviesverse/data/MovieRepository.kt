package com.powerusertech.moviesverse.data

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


@ViewModelScoped
class MovieRepository @Inject constructor(remoteMoviesDataSource: RemoteMoviesDataSource) {
    val remoteMovies = remoteMoviesDataSource
}