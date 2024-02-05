package com.powerusertech.moviesverse.presentation.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.powerusertech.moviesverse.R
import com.powerusertech.moviesverse.core.utils.Constants
import com.powerusertech.moviesverse.data.local.FavouriteMovieEntity
import com.powerusertech.moviesverse.data.models.moviedetails.MovieDetailsResponse
import com.powerusertech.moviesverse.databinding.SearchItemBinding

class WishListAdapter(private val onClick: (Int) -> Unit):ListAdapter<FavouriteMovieEntity, WishListAdapter.WishListHolder>(
    COMPARATOR) {
    inner class WishListHolder(val binding: SearchItemBinding):RecyclerView.ViewHolder(binding.root)
    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<FavouriteMovieEntity>(){
            override fun areItemsTheSame(
                oldItem: FavouriteMovieEntity,
                newItem: FavouriteMovieEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FavouriteMovieEntity,
                newItem: FavouriteMovieEntity
            ): Boolean {
                return oldItem.movieDetailsResponse == newItem.movieDetailsResponse
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListHolder {
        return WishListHolder(SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: WishListHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            root.setOnClickListener {
                onClick.invoke(item.id)
            }
            posterIv.load(Constants.BASE_POSTER_URL +item.movieDetailsResponse.posterPath){
                crossfade(100)
                    .error(R.drawable.poster)
                    .transformations(RoundedCornersTransformation(25f))
            }

            val title = item.movieDetailsResponse.title
            nameTv.text = title
            val averageVoting = item.movieDetailsResponse.voteAverage/2
            val rating = "$averageVoting"
            if (rating.length>=4) ratingTv.text = rating.subSequence(0, 4)
            else ratingTv.text = rating
            ratingBar.rating = averageVoting.toFloat()
            summaryTv.text = item.movieDetailsResponse.overview
            var genreList = ""
            for (genre in item.movieDetailsResponse.genres){
                    genreList += genre.name+", "
            }
            genreTv.text = genreList

        }
    }


}
