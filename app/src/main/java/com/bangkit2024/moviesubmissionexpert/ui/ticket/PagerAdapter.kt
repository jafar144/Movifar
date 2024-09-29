package com.bangkit2024.moviesubmissionexpert.ui.ticket

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        val fragment = ItemTicketsFragment()
        val bundle = Bundle()
        if (position == 0) {
            bundle.putString(ItemTicketsFragment.ARG_TAB, ItemTicketsFragment.TAB_UPCOMING)
        } else {
            bundle.putString(ItemTicketsFragment.ARG_TAB, ItemTicketsFragment.TAB_PASSED)
        }

        fragment.arguments = bundle
        return fragment
    }

    override fun getItemCount(): Int = 2
}