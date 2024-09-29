package com.bangkit2024.watch_list.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit2024.core.domain.model.WatchList
import com.bangkit2024.core.ui.WatchListAdapter
import com.bangkit2024.moviesubmissionexpert.databinding.FragmentWatchListBinding
import com.bangkit2024.moviesubmissionexpert.di.WatchListModuleDependencies
import com.bangkit2024.moviesubmissionexpert.ui.detail.DetailActivity
import com.bangkit2024.watch_list.di.DaggerWatchListComponent
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class WatchListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var _binding: FragmentWatchListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WatchListViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerWatchListComponent.builder()
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    WatchListModuleDependencies::class.java
                )
            )
            .context(requireContext())
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.getWatchListMovie().observe(viewLifecycleOwner) { watchListMovie ->
            if (watchListMovie.isNotEmpty()) {
                setupDataWatchList(watchListMovie)
                binding.tvNullWatchList.visibility = View.GONE
            } else {
                binding.tvNullWatchList.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvWatchList.layoutManager = gridLayoutManager
    }

    private fun setupDataWatchList(watchList: List<WatchList>)  {
        val adapter = WatchListAdapter { movie ->
            val intentToDetail = Intent(requireContext(), DetailActivity::class.java)
            intentToDetail.putExtra(DetailActivity.EXTRA_ID_MOVIE, movie.idMovie)
            startActivity(intentToDetail)
        }
        adapter.submitList(watchList)
        binding.rvWatchList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvWatchList.adapter = null
        _binding = null
    }

}