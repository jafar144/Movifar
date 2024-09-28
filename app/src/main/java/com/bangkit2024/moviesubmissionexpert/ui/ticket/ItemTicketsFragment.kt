package com.bangkit2024.moviesubmissionexpert.ui.ticket

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.core.domain.model.MovieTicket
import com.bangkit2024.core.ui.TicketAdapter
import com.bangkit2024.moviesubmissionexpert.databinding.FragmentItemTicketsBinding
import com.bangkit2024.core.ui.TicketReminderReceiver
import com.bangkit2024.moviesubmissionexpert.ui.ticket.detail.DetailTicketActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemTicketsFragment : Fragment() {

    private var _binding: FragmentItemTicketsBinding? = null
    private val binding get() = _binding!!

    private var tabName: String? = null

    private val movieTicketViewModel: TicketViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemTicketsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabName = arguments?.getString(ARG_TAB)

        if (tabName == TAB_UPCOMING) {
            movieTicketViewModel.getUpcomingMovieTicket().observe(viewLifecycleOwner) { movieTickets ->
                if (movieTickets.isEmpty()) {
                    binding.tvNullTicket.visibility = View.VISIBLE
                } else {
                    setupUpcomingRecyclerView()
                    binding.tvNullTicket.visibility = View.GONE
                    setupData(movieTickets)
                }
            }
        } else if (tabName == TAB_PASSED) {
            movieTicketViewModel.getWatchedMovieTicket().observe(viewLifecycleOwner) { movieTickets ->
                if (movieTickets.isEmpty()) {
                    binding.tvNullTicket.visibility = View.VISIBLE
                } else {
                    setupPassedRecyclerView()
                    binding.tvNullTicket.visibility = View.GONE
                    setupData(movieTickets)
                }
            }
        }
    }

    private fun setupUpcomingRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvTickets.layoutManager = linearLayoutManager
    }

    private fun setupPassedRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvTickets.layoutManager = linearLayoutManager
    }

    private fun setupData(movieTicket: List<MovieTicket>) {
        val movieTicketAdapter = TicketAdapter(
            ticketReminderReceiver = TicketReminderReceiver(),
            onItemClick = { ticket ->
                val intentToDetailTicket = Intent(requireContext(), DetailTicketActivity::class.java)
                intentToDetailTicket.putExtra(DetailTicketActivity.EXTRA_ID_TICKET, ticket.idTicket)
                intentToDetailTicket.putExtra(DetailTicketActivity.EXTRA_FROM, DetailTicketActivity.FROM_ITEM_TICKET)
                startActivity(intentToDetailTicket)
            },
            swOnCheckedChangeListener = { movieTickets, isOnReminder ->
                movieTicketViewModel.changeIsOnReminderTicket(movieTickets.idTicket, isOnReminder)
            }
        )
        movieTicketAdapter.submitList(movieTicket)
        binding.rvTickets.adapter = movieTicketAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvTickets.adapter = null
        _binding = null
    }

    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_UPCOMING = "upcoming"
        const val TAB_PASSED = "passed"
    }
}