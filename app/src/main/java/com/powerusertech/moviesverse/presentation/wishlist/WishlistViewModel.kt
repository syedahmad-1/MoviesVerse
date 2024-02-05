package com.powerusertech.moviesverse.presentation.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.powerusertech.moviesverse.data.local.FavouriteMovieEntity
import com.powerusertech.moviesverse.data.models.moviedetails.MovieDetailsResponse
import com.powerusertech.moviesverse.data.network.repository.MovieDetailsLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class WishlistViewModel @Inject constructor(private val localDataSource: MovieDetailsLocalDataSource) :
    ViewModel() {

    var favouriteMovies: LiveData<List<FavouriteMovieEntity>> = localDataSource.getAllMovies().asLiveData()

    fun deleteMovieById(){

    }
}