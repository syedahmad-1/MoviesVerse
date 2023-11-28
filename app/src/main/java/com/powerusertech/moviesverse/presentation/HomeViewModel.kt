package com.powerusertech.moviesverse.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.powerusertech.moviesverse.core.utils.NetworkResult
import com.powerusertech.moviesverse.data.TrendingRepository
import com.powerusertech.moviesverse.data.models.TrendingResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(application: Application, private val trendingRepository: TrendingRepository) :
    AndroidViewModel(application) {

    private val _trendingResponseLiveData = MutableLiveData<NetworkResult<TrendingResponse>>()
    val trendingMovieResponseLiveData get() = _trendingResponseLiveData

    private val _trendingAllByWeekLiveData = MutableLiveData<NetworkResult<TrendingResponse>>()
    val trendingAllByWeekLiveData get() = _trendingAllByWeekLiveData

    private val _trendingTvByWeekLiveData = MutableLiveData<NetworkResult<TrendingResponse>>()
    val trendingTvByWeekLiveData get() = _trendingTvByWeekLiveData

    fun getTrendingMoviesByWeek()=viewModelScope.launch{
        getTrendingMoviesSafeCall()
    }

    fun getTrendingAllByWeek()=viewModelScope.launch{
        getTrendingAllByWeekSafeCall()
    }

    fun getTrendingTvByWeek()=viewModelScope.launch {
        getTrendingTvByWeekSafeCall()
    }

    private suspend fun getTrendingTvByWeekSafeCall() {
        _trendingTvByWeekLiveData.value = NetworkResult.Loading()
        if (hasInternetConnection()){
            try {
                val response = trendingRepository.remoteTrending.getTrendingTvByWeek()
                _trendingTvByWeekLiveData.value = handleTrendingMovie(response)
            } catch (e:Exception){
                _trendingTvByWeekLiveData.value = NetworkResult.Error("Tv Shows Not Available")
            }
        }else{
            _trendingTvByWeekLiveData.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private suspend fun getTrendingAllByWeekSafeCall() {
        _trendingAllByWeekLiveData.value = NetworkResult.Loading()
        if (hasInternetConnection()){
            try {
                val response = trendingRepository.remoteTrending.getAllTrendingByWeek()
                _trendingAllByWeekLiveData.value = handleTrendingMovie(response)
            } catch (e:Exception){
                _trendingAllByWeekLiveData.value = NetworkResult.Error("Movies Not Available")
            }
        }else{
            _trendingAllByWeekLiveData.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private suspend fun getTrendingMoviesSafeCall() {
        _trendingResponseLiveData.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                Log.d("TAG", "getTrendingMoviesSafeCall: api request called")
                val response = trendingRepository.remoteTrending.getTrendingMovies()
                _trendingResponseLiveData.value = handleTrendingMovie(response)
            } catch (e: Exception) {
                _trendingResponseLiveData.value = NetworkResult.Error("Movies Not Available")

            }

        } else {
            _trendingResponseLiveData.value = NetworkResult.Error("No Internet Connection")
        }

    }

    private fun handleTrendingMovie(response: Response<TrendingResponse>): NetworkResult<TrendingResponse> {
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