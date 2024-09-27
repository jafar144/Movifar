package com.bangkit2024.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.core.BuildConfig
import com.bangkit2024.core.databinding.ItemMovieSmallBinding
import com.bangkit2024.core.domain.model.ItemSearch
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class FullMovieAdapter : ListAdapter<ItemSearch, FullMovieAdapter.MyViewHolder>(
    FULL_MOVIE_DIFF_CALLBACK
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FullMovieAdapter.MyViewHolder =
        MyViewHolder(
            ItemMovieSmallBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: FullMovieAdapter.MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyViewHolder(
        private val binding: ItemMovieSmallBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemSearch) {
            Glide.with(itemView.context)
                .load(BuildConfig.IMAGE_URL + item.posterPath)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivItemPosterMovie)
        }
    }

    companion object {
        private val FULL_MOVIE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemSearch>() {
            override fun areItemsTheSame(oldItem: ItemSearch, newItem: ItemSearch): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ItemSearch, newItem: ItemSearch): Boolean =
                oldItem == newItem
        }
    }
}