package com.powerusertech.moviesverse.data

import com.powerusertech.moviesverse.data.network.repository.SearchRemoteDataSource
import javax.inject.Inject

class SearchRepository @Inject constructor(remoteDataSource: SearchRemoteDataSource) {
    val remoteSearchResponse = remoteDataSource
}