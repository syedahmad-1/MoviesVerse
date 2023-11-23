package com.powerusertech.moviesverse.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.powerusertech.moviesverse.core.utils.NetworkResult
import com.powerusertech.moviesverse.data.MovieRepository
import com.powerusertech.moviesverse.data.models.TrendingMovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class HomeViewmodel @Inject constructor(application: Application, private val movieRepository: MovieRepository) :
    AndroidViewModel(application) {

    private val _trendingMovieResponseLiveData = MutableLiveData<NetworkResult<TrendingMovieResponse>>()
    val trendingMovieResponseLiveData get() = _trendingMovieResponseLiveData

    fun getTrendingMoviesByWeek()=viewModelScope.launch{
        getTrendingMoviesSafeCall()
    }

    private suspend fun getTrendingMoviesSafeCall() {
        _trendingMovieResponseLiveData.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = movieRepository.remoteMovies.getTrendingMovies()
                _trendingMovieResponseLiveData.value = handleTrendingMovie(response)
            } catch (e: Exception) {
                _trendingMovieResponseLiveData.value = NetworkResult.Error("Recipe Not Found")

            }

        } else {
            _trendingMovieResponseLiveData.value = NetworkResult.Error("No Internet Connection")
        }

    }

    private fun handleTrendingMovie(response: Response<TrendingMovieResponse>): NetworkResult<TrendingMovieResponse> {
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

    private fun hasInternetConnection(): Boolean {
        return true
    }


}