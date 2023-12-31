package com.powerusertech.moviesverse.data

import com.powerusertech.moviesverse.data.network.repository.TrendingRemoteDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


@ViewModelScoped
class TrendingRepository @Inject constructor(trendingRemoteDataSource: TrendingRemoteDataSource) {
    val remoteTrending = trendingRemoteDataSource
}