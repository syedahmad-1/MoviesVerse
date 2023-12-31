package com.powerusertech.moviesverse.presentation.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.powerusertech.moviesverse.core.utils.NetworkResult
import com.powerusertech.moviesverse.databinding.FragmentSearchBinding
import com.powerusertech.moviesverse.presentation.adapters.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {


    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel by viewModels<SearchViewModel>()
    private val searchAdapter by lazy { SearchAdapter{
        navigateToMovieDetails(it)
    }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            searchEditText.setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)
                ) {
                    // Handle the action here
                    val enteredText = searchEditText.text.toString()
                    searchUsingQuery(enteredText)
                    return@setOnEditorActionListener true
                }
                false
            }

            searchIcon.setOnClickListener {
                val enteredText = searchEditText.text.toString()
                searchUsingQuery(enteredText)
            }
            binding.searchRv.adapter = searchAdapter
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            binding.searchRv.layoutManager = linearLayoutManager
        }

    }

    private fun searchUsingQuery(query: String) {
        searchViewModel.searchByQuery(query)
        searchViewModel.searchResultLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Error -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }
                is NetworkResult.Loading -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                    binding.searchRv.visibility = View.INVISIBLE
                }
                is NetworkResult.Success -> {
                    binding.searchRv.visibility = View.VISIBLE
                    binding.loadingProgressBar.visibility = View.INVISIBLE
                    searchAdapter.submitList(it.data?.results)
                }
            }
        }
    }

    private fun navigateToMovieDetails(movieId:Int){
        val action = SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment(movieId)
        findNavController().navigate(action)
    }
}


