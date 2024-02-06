package com.powerusertech.moviesverse.presentation.wishlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.powerusertech.moviesverse.data.local.FavouriteMovieEntity
import com.powerusertech.moviesverse.databinding.FragmentWishlistBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistFragment : Fragment() {


    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private val TAG = "WISHLIST_FRAGMENT"


    private val wishlistViewModel by viewModels<WishlistViewModel>()
    private val wishListAdapter by lazy { WishListAdapter {} }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishlistBinding.inflate(layoutInflater)


        binding.searchEditText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                // Action is triggered when the search button on the keyboard is pressed
                val searchQuery = binding.searchEditText.text.toString()
                if (searchQuery.isNotBlank()){
                    wishlistViewModel.searchFavouriteMoviesByTitle(searchQuery)
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        observeData()
        setWishListRv()

        return binding.root
    }

    private fun updateUI(searchedMovies: List<FavouriteMovieEntity>) {
        if (searchedMovies.isNotEmpty()){
            wishListAdapter.submitList(searchedMovies)
        }
    }
    private fun setWishListRv() {
        binding.searchRv.adapter = wishListAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.searchRv.layoutManager = linearLayoutManager
    }

    private fun observeData() {
        wishlistViewModel.favouriteMovies.observe(viewLifecycleOwner) { favmovies ->
            if (favmovies.isNotEmpty()) {
                Log.d(TAG, "onCreateView: ${favmovies.size}")
                wishListAdapter.submitList(favmovies)
            } else {
                Log.d(TAG, "onCreateView: No Movies Found")
            }
        }

        lifecycleScope.launchWhenStarted {
            wishlistViewModel.searchFavouriteMovies.observe(viewLifecycleOwner) { searchedMovies ->
                // Update UI or perform actions based on the updated list of favorite movies
                Log.d(TAG, "observeData: ${searchedMovies.size}") // Access the list size instead of element 0
                if (searchedMovies.isNotEmpty()) {
                    updateUI(searchedMovies)
                } else {
                    // Handle the case where no movies are found
                    Toast.makeText(context, "No Movies Found", Toast.LENGTH_SHORT).show()
                    // Provide appropriate UI feedback to the user
                }
            }
        }
    }

}