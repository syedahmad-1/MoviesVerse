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
import com.powerusertech.moviesverse.data.models.ResultX
import com.powerusertech.moviesverse.databinding.HomeItemBinding

class HomeAdapter :
    ListAdapter<ResultX, HomeAdapter.HomeViewHolder>(COMPARATOR) {
    inner class HomeViewHolder(val binding: HomeItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            HomeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {

            posterIv.load(BASE_POSTER_URL + item.posterPath) {
                crossfade(100)
                    .error(R.drawable.poster)
                    .transformations(RoundedCornersTransformation(25f))
            }

            val title = item.title ?: (item.name ?: item.originalTitle)
            nameTv.text = title
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