package com.bangkit2024.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.core.R
import com.bangkit2024.core.BuildConfig
import com.bangkit2024.core.databinding.ItemCastBinding
import com.bangkit2024.core.domain.model.CastItemDomain
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class CastAdapter : ListAdapter<CastItemDomain, CastAdapter.MyViewHolder>(CAST_DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    class MyViewHolder(
        private val binding: ItemCastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(caster: CastItemDomain) {
            Glide.with(itemView.context)
                .load(BuildConfig.IMAGE_URL + caster.profilePath)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivItemPosterCast)

            binding.tvItemOriginalNameCast.text = caster.originalName
            binding.tvItemCharacterCast.text =
                itemView.context.getString(R.string.caster_character, caster.character)
        }
    }

    companion object {
        private val CAST_DIFF_CALLBACK = object : DiffUtil.ItemCallback<CastItemDomain>() {
            override fun areItemsTheSame(
                oldItem: CastItemDomain,
                newItem: CastItemDomain
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CastItemDomain,
                newItem: CastItemDomain
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}