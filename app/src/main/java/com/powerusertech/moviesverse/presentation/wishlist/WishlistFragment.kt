package com.powerusertech.moviesverse.presentation.wishlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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



        observeData()
        setWishListRv()

        return binding.root
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
    }

}