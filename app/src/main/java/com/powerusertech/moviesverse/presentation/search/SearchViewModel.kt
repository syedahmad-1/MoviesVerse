package com.powerusertech.moviesverse.presentation.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.powerusertech.moviesverse.core.utils.NetworkResult
import com.powerusertech.moviesverse.data.SearchRepository
import com.powerusertech.moviesverse.data.models.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    application: Application,
    private val searchRepository: SearchRepository
) : AndroidViewModel(application) {
    private val _searchResultLiveData = MutableLiveData<NetworkResult<SearchResult>>()
    val searchResultLiveData: LiveData<NetworkResult<SearchResult>> get() = _searchResultLiveData

    fun searchByQuery(query: String) = viewModelScope.launch {
        searchResultsSafeApiCall(query)
    }

    private fun hasInternetConnection(): Boolean {
        return true
    }

    private suspend fun searchResultsSafeApiCall(query: String) {
        _searchResultLiveData.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = searchRepository.remoteSearchResponse.searchByQuery(query)
                _searchResultLiveData.value = handleSearchResponse(response)
            }catch (e:Exception){
                _searchResultLiveData.value = NetworkResult.Error(e.message)
            }
        }
        else{
            _searchResultLiveData.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun handleSearchResponse(response: Response<SearchResult>): NetworkResult<SearchResult> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limit Exceeded")
            }

            response.isSuccessful -> {
                val response = response.body()
                return NetworkResult.Success(response!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }
}