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
import com.powerusertech.moviesverse.data.models.Result
import com.powerusertech.moviesverse.databinding.HomeItemBinding

class HomeAdapter: ListAdapter<Result, HomeAdapter.HomeViewHolder>(COMPARATOR) {
    inner class HomeViewHolder(val binding:HomeItemBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(HomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {

            posterIv.load(BASE_POSTER_URL+item.posterPath){
                crossfade(100)
                    .error(R.drawable.poster)
                    .transformations(RoundedCornersTransformation(25f))
            }

            nameTv.text = item.title
        }
    }

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<Result>(){
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

        }
    }
}