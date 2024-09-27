package com.bangkit2024.moviesubmissionexpert.ui.now_playing

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit2024.core.data.source.remote.retrofit.Result
import com.bangkit2024.core.ui.FullMovieAdapter
import com.bangkit2024.core.utils.ext.showToast
import com.bangkit2024.moviesubmissionexpert.R
import com.bangkit2024.moviesubmissionexpert.databinding.ActivityNowPlayingBinding
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import dagger.hilt.android.AndroidEntryPoint
import com.bangkit2024.core.R as CoreR

@AndroidEntryPoint
class NowPlayingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNowPlayingBinding
    private val viewModel: NowPlayingViewModel by viewModels()

    private lateinit var skeletonNowPlaying: Skeleton
    private lateinit var movieAdapter: FullMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNowPlayingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieAdapter = FullMovieAdapter()
        binding.rvNowPlaying.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(this@NowPlayingActivity, 3)
        }

        viewModel.searchResult.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        setupSkeleton()
                        skeletonNowPlaying.showSkeleton()
                    }

                    is Result.Success -> {
                        skeletonNowPlaying.showOriginal()

                        if (binding.searchNowPlaying.query.isNotBlank()) {
                            binding.tvEmptySearch.visibility = if (result.data.isNotEmpty()) View.GONE else View.VISIBLE
                            movieAdapter.submitList(result.data)
                        }
                    }

                    is Result.Error -> {
                        skeletonNowPlaying.showOriginal()
                        showToast(result.error)
                    }
                }
            } else {
                showToast("Something went wrong")
            }
        }

        binding.searchNowPlaying.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isBlank() == true) {
                    binding.tvEmptySearch.visibility = View.VISIBLE
                    movieAdapter.submitList(listOf())
                }

                viewModel.setSearchQuery(newText ?: "")

                return true
            }
        })

        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupSkeleton() {
        skeletonNowPlaying = binding.rvNowPlaying.applySkeleton(CoreR.layout.item_watch_list)
        
        skeletonNowPlaying.maskColor = R.color.card_dark
        skeletonNowPlaying.shimmerColor = R.color.stroke_grey
        skeletonNowPlaying.shimmerDurationInMillis = 1000L
        skeletonNowPlaying.maskCornerRadius = 50F
    }
}