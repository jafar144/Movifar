package com.bangkit2024.moviesubmissionexpert.ui.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.moviesubmissionexpert.R
import com.bangkit2024.moviesubmissionexpert.databinding.FragmentTicketBinding
import com.google.android.material.tabs.TabLayoutMediator

class TicketFragment : Fragment() {

    private var _binding: FragmentTicketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerAdapter = PagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        binding.viewPager.adapter = pagerAdapter

        val viewPager = binding.viewPager.apply {
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        TabLayoutMediator(binding.tabsLayout, viewPager) { tab, position ->
            tab.text = getString(TAB_TITLE[position])
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewPager.adapter = null
        _binding = null
    }

    companion object {
        private val TAB_TITLE = intArrayOf(
            R.string.upcoming, R.string.passed
        )
    }

}