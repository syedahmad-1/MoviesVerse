package com.powerusertech.moviesverse.presentation.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.powerusertech.moviesverse.data.local.FavouriteMovieEntity
import com.powerusertech.moviesverse.data.network.repository.MovieDetailsLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class WishlistViewModel @Inject constructor(private val localDataSource: MovieDetailsLocalDataSource) :
    ViewModel() {

    var favouriteMovies: LiveData<List<FavouriteMovieEntity>> =
        localDataSource.getAllMovies().asLiveData()
    private val _searchFavoriteMovies = MutableLiveData<List<FavouriteMovieEntity>>()
    val searchFavouriteMovies: LiveData<List<FavouriteMovieEntity>> get() = _searchFavoriteMovies


    fun searchFavouriteMoviesByTitle(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultMovie = localDataSource.searchMovieByTitle(title)
            withContext(Dispatchers.Main) {
                _searchFavoriteMovies.postValue(resultMovie)
            }
        }

    }


}
