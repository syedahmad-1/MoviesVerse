package com.powerusertech.moviesverse.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.powerusertech.moviesverse.core.utils.Constants.BASE_POSTER_URL
import com.powerusertech.moviesverse.data.models.moviedetails.Cast
import com.powerusertech.moviesverse.databinding.CastItemBinding

class CastingAdapter(): ListAdapter<Cast, CastingAdapter.CastingViewHolder>(COMPARATOR) {

    inner class CastingViewHolder(val binding:CastItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastingViewHolder {
        return CastingViewHolder(CastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CastingViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.apply {
            castIv.load(BASE_POSTER_URL+item.profileUrl){
                crossfade(200)
                    .transformations(CircleCropTransformation())
            }
            nameTv.text = item.name
        }
    }

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem.name== newItem.name
            }

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem==newItem
            }

        }
    }
}