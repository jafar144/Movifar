package com.bangkit2024.moviesubmissionexpert.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.core.data.source.remote.retrofit.Result
import com.bangkit2024.core.domain.model.MovieItem
import com.bangkit2024.core.ui.MovieAdapter
import com.bangkit2024.core.ui.SetOnClickCallback
import com.bangkit2024.core.utils.ext.showToast
import com.bangkit2024.moviesubmissionexpert.R
import com.bangkit2024.core.R as CoreR
import com.bangkit2024.moviesubmissionexpert.databinding.FragmentHomeBinding
import com.bangkit2024.moviesubmissionexpert.ui.detail.DetailActivity
import com.bangkit2024.moviesubmissionexpert.ui.now_playing.NowPlayingActivity
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var skeletonNowPlaying: Skeleton
    private lateinit var skeletonUpComing: Skeleton

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSkeleton()

        homeViewModel.getNowPlayingMovie().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        skeletonNowPlaying.showSkeleton()
                    }

                    is Result.Success -> {
                        skeletonNowPlaying.showOriginal()

                        val nowPlayingMovies = result.data
                        setupDataNowPlaying(nowPlayingMovies)
                    }

                    is Result.Error -> {
                        skeletonNowPlaying.showOriginal()
                        showToast(result.error)
                    }
                }
            }
        }

        homeViewModel.getUpcomingMovie().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        skeletonUpComing.showSkeleton()
                    }

                    is Result.Success -> {
                        skeletonUpComing.showOriginal()

                        val comingSoonMovies = result.data
                        setupDataComingSoon(comingSoonMovies)
                    }

                    is Result.Error -> {
                        skeletonUpComing.showOriginal()
                        showToast(result.error)
                    }
                }
            }
        }

        binding.tvViewAllNowPlaying.setOnClickListener {
            Log.d("HomeFragment", "Yea")
            startActivity(Intent(requireActivity(), NowPlayingActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        val nowPlayingLayoutManager = LinearLayoutManager(requireContext())
        nowPlayingLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvNowPlaying.layoutManager = nowPlayingLayoutManager
        skeletonNowPlaying = binding.rvNowPlaying.applySkeleton(CoreR.layout.item_movie_small)

        val nowUpComingLayoutManager = LinearLayoutManager(requireContext())
        nowUpComingLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvComingSoon.layoutManager = nowUpComingLayoutManager
        skeletonUpComing = binding.rvComingSoon.applySkeleton(CoreR.layout.item_movie_small)
    }

    private fun setupDataNowPlaying(movie: List<MovieItem?>?) {
        val movieAdapter = MovieAdapter()
        movieAdapter.submitList(movie)

        binding.rvNowPlaying.adapter = movieAdapter

        movieAdapter.setOnItemClickCallback(object : SetOnClickCallback {
            override fun onClick(movie: MovieItem) {
                val intentToDetail = Intent(requireContext(), DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.EXTRA_ID_MOVIE, movie.id)
                startActivity(intentToDetail)
            }
        })
    }

    private fun setupDataComingSoon(movie: List<MovieItem?>?) {
        val movieAdapter = MovieAdapter()
        movieAdapter.submitList(movie)

        binding.rvComingSoon.adapter = movieAdapter

        movieAdapter.setOnItemClickCallback(object : SetOnClickCallback {
            override fun onClick(movie: MovieItem) {
                val intentToDetail = Intent(requireContext(), DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.EXTRA_ID_MOVIE, movie.id)
                startActivity(intentToDetail)
            }
        })
    }

    private fun setupSkeleton() {
        skeletonNowPlaying.maskColor = R.color.card_dark
        skeletonNowPlaying.shimmerColor = R.color.stroke_grey
        skeletonNowPlaying.shimmerDurationInMillis = 1000L
        skeletonNowPlaying.maskCornerRadius = 50F

        skeletonUpComing.maskColor = R.color.card_dark
        skeletonUpComing.shimmerColor = R.color.stroke_grey
        skeletonUpComing.shimmerDurationInMillis = 1000L
        skeletonUpComing.maskCornerRadius = 50F
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvComingSoon.adapter = null
        binding.rvNowPlaying.adapter = null
        _binding = null
    }
}