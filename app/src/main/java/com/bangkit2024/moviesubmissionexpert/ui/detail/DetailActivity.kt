package com.bangkit2024.moviesubmissionexpert.ui.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.core.data.source.remote.retrofit.Result
import com.bangkit2024.core.domain.model.CastItemDomain
import com.bangkit2024.core.domain.model.CrewItemDomain
import com.bangkit2024.core.domain.model.DetailMovie
import com.bangkit2024.core.domain.model.WatchList
import com.bangkit2024.core.ui.CastAdapter
import com.bangkit2024.core.utils.ext.showToast
import com.bangkit2024.core.BuildConfig
import com.bangkit2024.moviesubmissionexpert.R
import com.bangkit2024.moviesubmissionexpert.databinding.ActivityDetailBinding
import com.bangkit2024.moviesubmissionexpert.ui.booking_seat.BookingSeatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    private lateinit var title: String
    private lateinit var imageMovie: String

    private var isWatchList = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupOnBackPressed()
        setupRecyclerView()

        val movieId = intent.getIntExtra(EXTRA_ID_MOVIE, 0)

        detailViewModel.getDetailMovie(movieId).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.skeletonDetail.showSkeleton()
                    }

                    is Result.Success -> {
                        binding.skeletonDetail.showOriginal()
                        setupDetailMovie(result.data)
                        setupCastData(result.data.credits?.cast)
                    }

                    is Result.Error -> {
                        binding.skeletonDetail.showOriginal()
                        showToast(result.error)
                    }
                }
            } else {
                showToast("Terjadi kesalahan")
            }
        }

        detailViewModel.getStatusWatchListMovie(movieId).observe(this) { status ->
            isWatchList = status
            binding.ivAddWatchlist.setImageResource(
                if (isWatchList) R.drawable.heart_solid
                else R.drawable.heart_outline
            )
        }

        binding.btnContinue.setOnClickListener {
            val intentToBookingSeatView = Intent(this, BookingSeatActivity::class.java)

            intentToBookingSeatView.putExtra(BookingSeatActivity.EXTRA_ID_MOVIE, movieId)
            startActivity(intentToBookingSeatView)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.slide_in_up, R.anim.no_animation)
            } else {
                overridePendingTransition(R.anim.slide_in_up, R.anim.no_animation)
            }
        }

        binding.ivAddWatchlist.setOnClickListener {
            val watchListMovie = WatchList(
                idMovie = movieId,
                titleMovie = title,
                imageMovie = imageMovie
            )
            if (isWatchList) {
                detailViewModel.deleteWatchListMovie(movieId)
            } else {
                showToast("Added to Watchlist")
                detailViewModel.insertWatchListMovie(watchListMovie)
            }
        }
    }

    private fun setupDetailMovie(detailMovie: DetailMovie) {
        with(binding) {
            imageMovie = detailMovie.posterPath.toString()
            Glide.with(this@DetailActivity)
                .load(BuildConfig.IMAGE_URL + imageMovie)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivPosterMovieDetail)

            title = detailMovie.originalTitle.toString()
            tvTitleMovieDetail.text = title

            val durationMovie = "${detailMovie.runtime} Minutes"
            tvDuration.text = durationMovie
            tvDirector.text = getDirector(detailMovie)
            tvGenre.text = detailMovie.genres?.joinToString { it?.name.toString() }
            tvRating.text = getRating(detailMovie.adult)
            tvScore.text = detailMovie.popularity.toString()
            tvSynopsis.text = detailMovie.overview
        }
    }

    private fun getDirector(detailMovie: DetailMovie): String? {
        val director = detailMovie.credits?.run {
            cast?.find { it?.knownForDepartment == "Directing" }
                ?: crew?.find { it?.knownForDepartment == "Directing" }
        }

        return when (director) {
            is CastItemDomain -> director.originalName
            is CrewItemDomain -> director.originalName
            else -> "-"
        }
    }

    private fun getRating(rating: Boolean?): String =
        if (rating == true) "17+" else "13+"

    private fun setupOnBackPressed() {
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvCast.layoutManager = linearLayoutManager
    }

    private fun setupCastData(casts: List<CastItemDomain?>?) {
        val castAdapter = CastAdapter()
        castAdapter.submitList(casts)
        binding.rvCast.adapter = castAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.rvCast.adapter = null
    }

    companion object {
        const val EXTRA_ID_MOVIE = "extra_id_movie"
    }
}