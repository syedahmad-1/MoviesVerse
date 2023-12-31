package com.powerusertech.moviesverse.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.powerusertech.moviesverse.R
import com.powerusertech.moviesverse.core.utils.Constants
import com.powerusertech.moviesverse.data.models.ResultX
import com.powerusertech.moviesverse.databinding.HomeCardItemBinding

class CardItemAdapter : ListAdapter<ResultX, CardItemAdapter.CardItemViewHolder>(COMPARATOR) {
    inner class CardItemViewHolder(val binding: HomeCardItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardItemViewHolder {
        return CardItemViewHolder(
            HomeCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            posterIv.load(Constants.BASE_POSTER_URL + item.posterPath) {
                crossfade(30)
                    .error(R.drawable.poster)
                    .transformations(RoundedCornersTransformation(25f))
            }

            val title = item.title ?: (item.name ?: item.originalTitle)
            nameTv.text = title
            val averageVoting = item.voteAverage / 2
            val rating = "$averageVoting"
            if (rating.length >= 4) ratingTv.text = rating.subSequence(0, 4)
            else ratingTv.text = rating
            ratingBar.rating = averageVoting.toFloat()

        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ResultX>() {
            override fun areItemsTheSame(oldItem: ResultX, newItem: ResultX): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResultX, newItem: ResultX): Boolean {
                return oldItem == newItem
            }

        }
    }
}