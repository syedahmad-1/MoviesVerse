package com.powerusertech.moviesverse

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.powerusertech.moviesverse.core.utils.Constants.BASE_POSTER_URL
import com.powerusertech.moviesverse.core.utils.NetworkResult
import com.powerusertech.moviesverse.data.models.moviedetails.MovieDetailsResponse
import com.powerusertech.moviesverse.databinding.FragmentMovieDetailsBinding
import com.powerusertech.moviesverse.presentation.adapters.CastingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private val movieDetailsViewModel by viewModels<MovieDetailsViewModel>()
    private var _binding: FragmentMovieDetailsBinding? = null

    private val args by navArgs<MovieDetailsFragmentArgs>()

    private val binding get() = _binding!!
    private val TAG = "MOVIEDETAILSFRAGMENT"
    private val castingAdapter by lazy { CastingAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(layoutInflater)

        movieDetailsViewModel.getMoviesById(args.movieId)
        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }
        hideViews()
        observeLiveData()

        return binding.root
    }

    private fun hideViews() {
        binding.mainContent.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun observeLiveData() {
        movieDetailsViewModel.movieDetailsResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Error -> {
                    Log.d(TAG, "onCreateView: ${it.message} error occurred")
                    hideViews()
                }

                is NetworkResult.Loading -> {
                    Log.d(TAG, "onCreateView: Loading...")
                    hideViews()
                }

                is NetworkResult.Success -> {
                    Log.d(TAG, "onCreateView: ${it.data}")
                    showViews()
                    setData(it.data!!)
                }
            }
        }
    }

    private fun showViews() {
        binding.mainContent.visibility = View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun setData(data: MovieDetailsResponse) {
        binding.apply {
            backDropIv.load(BASE_POSTER_URL + data.backdropPath) {
                error(R.drawable.poster)
                    .crossfade(200)
            }
            posterIv.load(BASE_POSTER_URL + data.posterPath) {
                error(R.drawable.poster)
                    .crossfade(200)
            }
            nameTv.text = data.originalTitle
            descriptionTv.text = data.overview
            ratingTv.text = String.format("%.2f", data.voteAverage / 2)
            ratingBar.rating = (data.voteAverage.toFloat()) / 2
            val language = "(${data.originalLanguage})"
            languageTv.text = language

            var genreText = ""

            castsRv.adapter = castingAdapter
            val castingLayoutManager = LinearLayoutManager(requireContext())
            castingLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            castsRv.layoutManager = castingLayoutManager
            castingAdapter.submitList(data.credits.cast)


            data.apply {
                for (genre in genres) {
                    genreText += "${genre.name} "
                }
            }
            categoriesTv.text = genreText

        }

    }

}