package com.bangkit2024.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.core.BuildConfig
import com.bangkit2024.core.databinding.ItemWatchListBinding
import com.bangkit2024.core.domain.model.WatchList
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class WatchListAdapter : ListAdapter<WatchList, WatchListAdapter.MyViewHolder>(WATCH_LIST_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemWatchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MyViewHolder(
        private val binding: ItemWatchListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WatchList) {
            Glide.with(itemView.context)
                .load(BuildConfig.IMAGE_URL + item.imageMovie)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivItemPosterMovie)

            binding.tvItemTitle.text = item.titleMovie
        }
    }

    companion object {
        private val WATCH_LIST_DIFF_CALLBACK = object : DiffUtil.ItemCallback<WatchList>() {
            override fun areItemsTheSame(oldItem: WatchList, newItem: WatchList): Boolean {
                return oldItem.idMovie == newItem.idMovie
            }

            override fun areContentsTheSame(oldItem: WatchList, newItem: WatchList): Boolean {
                return oldItem == newItem
            }
        }
    }
}