package com.bangkit2024.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.core.BuildConfig
import com.bangkit2024.core.databinding.ItemTicketBinding
import com.bangkit2024.core.domain.model.MovieTicket
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class TicketAdapter(
    private val ticketReminderReceiver: TicketReminderReceiver,
    private val onItemClick: (MovieTicket) -> Unit,
    private val swOnCheckedChangeListener: (MovieTicket, Boolean) -> Unit
) : ListAdapter<MovieTicket, TicketAdapter.MyViewHolder>(TICKET_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyViewHolder(
        private val binding: ItemTicketBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieTicket: MovieTicket) {
            binding.swRemindMe.isChecked = movieTicket.isOnReminder

            Glide.with(itemView.context)
                .load(BuildConfig.IMAGE_URL + movieTicket.imageMovie)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivPosterMovieTicket)

            binding.tvTitleMovieTicket.text = movieTicket.titleMovie
            binding.tvDateMovieTicket.text = movieTicket.date
            binding.tvTimeMovieTicket.text = movieTicket.time

            binding.swRemindMe.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    ticketReminderReceiver.setTicketReminder(itemView.context, movieTicket)
                } else {
                    ticketReminderReceiver.cancelTicketReminder(itemView.context, movieTicket)
                }
                swOnCheckedChangeListener(movieTicket, isChecked)
            }

            itemView.setOnClickListener {
                onItemClick(movieTicket)
            }
        }
    }

    companion object {
        private val TICKET_DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieTicket>() {
            override fun areItemsTheSame(oldItem: MovieTicket, newItem: MovieTicket): Boolean {
                return oldItem.idTicket == newItem.idTicket
            }

            override fun areContentsTheSame(oldItem: MovieTicket, newItem: MovieTicket): Boolean {
                return oldItem == newItem
            }
        }
    }
}