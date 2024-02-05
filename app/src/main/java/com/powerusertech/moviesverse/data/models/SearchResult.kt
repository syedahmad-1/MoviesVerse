package com.powerusertech.moviesverse.data.models


import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieDetailsResponse>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)