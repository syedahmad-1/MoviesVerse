package com.powerusertech.moviesverse.data.network.api

import com.powerusertech.moviesverse.data.models.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search/multi")
    suspend fun searchByQuery(@Query("query") query: String): Response<SearchResult>
}
