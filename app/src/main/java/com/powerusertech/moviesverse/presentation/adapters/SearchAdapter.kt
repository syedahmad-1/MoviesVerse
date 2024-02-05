package com.powerusertech.moviesverse.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.powerusertech.moviesverse.R
import com.powerusertech.moviesverse.core.utils.Constants.BASE_POSTER_URL
import com.powerusertech.moviesverse.data.models.MovieDetailsResponse
import com.powerusertech.moviesverse.databinding.SearchItemBinding

class SearchAdapter(private val onClick:(Int)->Unit): ListAdapter<MovieDetailsResponse, SearchAdapter.SearchViewHolder>(COMPARATOR) {
    inner class SearchViewHolder(val binding:SearchItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    private val genreMapping = mapOf(
        10759 to "Action & Adventure",
        16 to "Animation",
        35 to "Comedy",
        80 to "Crime",
        99 to "Documentary",
        18 to "Drama",
        10751 to "Family",
        10762 to "Kids",
        9648 to "Mystery",
        10763 to "News",
        10764 to "Reality",
        10765 to "Sci-Fi & Fantasy",
        10766 to "Soap",
        10767 to "Talk",
        10768 to "War & Politics",
        37 to "Western",
        28 to "Action",
        12 to "Adventure",
        14 to "Fantasy",
        36 to "History",
        27 to "Horror",
        10402 to "Music",
        10749 to "Romance",
        878 to "Science Fiction",
        10770 to "TV Movie",
        53 to "Thriller",
        10752 to "War"
    )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            root.setOnClickListener {
                onClick.invoke(item.id)
            }
            posterIv.load(BASE_POSTER_URL +item.posterPath){
                crossfade(100)
                    .error(R.drawable.poster)
                    .transformations(RoundedCornersTransformation(25f))
            }

            val title = item.title ?: (item.name ?: item.originalTitle)
            nameTv.text = title
            val averageVoting = item.voteAverage/2
            val rating = "$averageVoting"
            if (rating.length>=4) ratingTv.text = rating.subSequence(0, 4)
            else ratingTv.text = rating
            ratingBar.rating = averageVoting.toFloat()
            summaryTv.text = item.overview
            var genreList = ""
            for (genre in item.genreIds){
                if (genreMapping.containsKey(genre)){
                    genreList += "${genreMapping[genre]}, "
                }
            }
            genreTv.text = genreList

        }
    }

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<MovieDetailsResponse>(){
            override fun areItemsTheSame(oldItem: MovieDetailsResponse, newItem: MovieDetailsResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieDetailsResponse, newItem: MovieDetailsResponse): Boolean {
                return oldItem == newItem
            }

        }
    }
}