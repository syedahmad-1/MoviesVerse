package com.powerusertech.moviesverse.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.powerusertech.moviesverse.core.utils.NetworkResult
import com.powerusertech.moviesverse.databinding.FragmentHomeBinding
import com.powerusertech.moviesverse.presentation.adapters.CardItemAdapter
import com.powerusertech.moviesverse.presentation.adapters.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()

    private val homeAdapter by lazy { HomeAdapter{
        navigateToMovieDetailsFragment(it)
    } }

    private fun navigateToMovieDetailsFragment(movieId: Int) {
        Toast.makeText(requireContext(), "movieId $movieId", Toast.LENGTH_SHORT).show()
        val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(movieId)
        findNavController().navigate(action)
    }

    private val allTrendingAdapter by lazy { CardItemAdapter() }
    private val trendingTvAdapter by lazy { HomeAdapter{
        navigateToMovieDetailsFragment(it)
    } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(layoutInflater)
        setUpObserver()

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val horizontalLayoutManager = LinearLayoutManager(context)
        horizontalLayoutManager.orientation =LinearLayoutManager.HORIZONTAL

        val horizontalLayoutManager2 = LinearLayoutManager(context)
        horizontalLayoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        val horizontalLayoutManager3 = LinearLayoutManager(context)
        horizontalLayoutManager3.orientation = LinearLayoutManager.HORIZONTAL


        binding.trendingAllRv.layoutManager = horizontalLayoutManager
        binding.trendingAllRv.adapter = allTrendingAdapter

        binding.bestMovieRv.layoutManager = horizontalLayoutManager2
        binding.bestMovieRv.adapter =  homeAdapter

        binding.topRatedOnTvRv.layoutManager = horizontalLayoutManager3
        binding.topRatedOnTvRv.adapter =  trendingTvAdapter


    }

    private fun setUpObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                homeViewModel.trendingAllByWeekLiveData.observe(viewLifecycleOwner){
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
                            allTrendingAdapter.submitList(result)
                        }
                    }
                }
                homeViewModel.trendingTvByWeekLiveData.observe(viewLifecycleOwner){
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
                            trendingTvAdapter.submitList(result)
                        }
                    }
                }

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
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}