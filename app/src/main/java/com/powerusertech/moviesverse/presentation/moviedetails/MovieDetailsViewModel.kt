package com.powerusertech.moviesverse.presentation.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.powerusertech.moviesverse.core.utils.NetworkResult
import com.powerusertech.moviesverse.data.local.FavouriteMovieEntity
import com.powerusertech.moviesverse.data.models.moviedetails.MovieDetailsResponse
import com.powerusertech.moviesverse.data.network.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val _moveDetailsLiveData = MutableLiveData<NetworkResult<MovieDetailsResponse>>()
    val movieDetailsResponseLiveData:LiveData<NetworkResult<MovieDetailsResponse>> get() = _moveDetailsLiveData

    private val _tvDetailsLiveData = MutableLiveData<NetworkResult<MovieDetailsResponse>>()
    val tvDetailsLiveData:LiveData<NetworkResult<MovieDetailsResponse>> get() = _tvDetailsLiveData


    fun getMoviesById(movieId: Int) = viewModelScope.launch {
        getMoviesByIdSafeCall(movieId)
    }

    fun getTvById(tvId:Int)=viewModelScope.launch {
        getTvByIdSafeCall(tvId)
    }

    private suspend fun getTvByIdSafeCall(tvId: Int) {
        _tvDetailsLiveData.postValue(NetworkResult.Loading())
        if (hasInternetConnection()){
            try {
                val response = movieRepository.remote.getTvById(tvId)
                _tvDetailsLiveData.postValue(handleMovieDetails(response))
            }catch (e:Exception){
                _tvDetailsLiveData.postValue(NetworkResult.Error(e.message))
            }
        }else{
            _tvDetailsLiveData.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }


    fun addMovieToFavorites(movie:MovieDetailsResponse){
        val movieEntity = FavouriteMovieEntity(movie)
        insertMovie(movieEntity)
    }

    private fun insertMovie(movieEntity: FavouriteMovieEntity){
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.local.addFavoriteMovies(movieEntity)
        }
    }

    private suspend fun getMoviesByIdSafeCall(movieId: Int) {
        _moveDetailsLiveData.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = movieRepository.remote.getMovieById(movieId)
                _moveDetailsLiveData.value = handleMovieDetails(response)
            } catch (e: Exception) {
                _moveDetailsLiveData.value = NetworkResult.Error(e.message)
            }
        } else {
            _moveDetailsLiveData.value = NetworkResult.Error("No Internet Connection")
        }

    }

    private fun handleMovieDetails(response: Response<MovieDetailsResponse>): NetworkResult<MovieDetailsResponse> {
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