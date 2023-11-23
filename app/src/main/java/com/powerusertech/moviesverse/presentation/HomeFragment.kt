package com.powerusertech.moviesverse.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.powerusertech.moviesverse.core.utils.NetworkResult
import com.powerusertech.moviesverse.databinding.FragmentHomeBinding
import com.powerusertech.moviesverse.presentation.adapters.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewmodel>()

    private val homeAdapter by lazy { HomeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val horizontalLayoutManager = LinearLayoutManager(context)
        horizontalLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val horizontalLayoutManager2 = LinearLayoutManager(context)
        horizontalLayoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        val horizontalLayoutManager3 = LinearLayoutManager(context)
        horizontalLayoutManager3.orientation = LinearLayoutManager.HORIZONTAL


        binding.trendingAllRv.layoutManager = horizontalLayoutManager
        binding.trendingAllRv.adapter = homeAdapter

        binding.bestMovieRv.layoutManager = horizontalLayoutManager2
        binding.bestMovieRv.adapter =  homeAdapter

        binding.topRatedOnTvRv.layoutManager = horizontalLayoutManager3
        binding.topRatedOnTvRv.adapter =  homeAdapter

        homeViewModel.getTrendingMoviesByWeek()
        homeViewModel.trendingMovieResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Error -> {
                    Log.d("TAG", "onCreateView: ${it.message}")
                }

                is NetworkResult.Loading -> {
                    Log.d("TAG", "onViewCreated: Loading...")
                }

                is NetworkResult.Success -> {
                    val result = it.data?.results
                    Log.d("TAG", "onCreateView: $result")
                    homeAdapter.submitList(result)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}