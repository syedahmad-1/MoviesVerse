package com.powerusertech.moviesverse.data.network

import com.powerusertech.moviesverse.data.models.SearchResult
import com.powerusertech.moviesverse.data.network.Api.SearchApi
import retrofit2.Response
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(private val searchApi: SearchApi) {
    suspend fun searchByQuery(query: String): Response<SearchResult> {
        return searchApi.searchByQuery(query)
    }
}