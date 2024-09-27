package com.bangkit2024.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.core.BuildConfig
import com.bangkit2024.core.databinding.ItemMovieSmallBinding
import com.bangkit2024.core.domain.model.MovieItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MovieAdapter : ListAdapter<MovieItem, MovieAdapter.MyViewHolder>(MOVIE_DIFF_CALLBACK) {

    private var setOnClickCallback: SetOnClickCallback? = null

    fun setOnItemClickCallback(setOnClickCallback: SetOnClickCallback) {
        this.setOnClickCallback = setOnClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemMovieSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))

        // Set margin only for the first item
        if (position == 0) {
            holder.itemView.layoutParams =
                (holder.itemView.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    marginStart = 40
                }
        }
    }

    inner class MyViewHolder(
        private val binding: ItemMovieSmallBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieItem) {
            Glide.with(itemView.context)
                .load(BuildConfig.IMAGE_URL + item.posterPath)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivItemPosterMovie)

            itemView.setOnClickListener {
                setOnClickCallback?.onClick(item)
            }
        }
    }

    companion object {
        private val MOVIE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieItem>() {
            override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

interface SetOnClickCallback {
    fun onClick(movie: MovieItem)
}