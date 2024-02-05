package com.powerusertech.moviesverse.data.network.repository

import javax.inject.Inject

class MovieRepository @Inject constructor(remoteDataSource: MovieDetailsRemoteDataSource, localDataSource: MovieDetailsLocalDataSource) {
    val remote = remoteDataSource
    val local = localDataSource
}