package com.bangkit2024.moviesubmissionexpert.ui.overview

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.core.domain.model.DetailMovie
import com.bangkit2024.core.domain.model.MovieTicket
import com.bangkit2024.core.utils.GenerateTicketCode
import com.bangkit2024.core.BuildConfig
import com.bangkit2024.moviesubmissionexpert.R
import com.bangkit2024.core.data.source.remote.retrofit.Result
import com.bangkit2024.core.utils.BiometricAuthListener
import com.bangkit2024.core.utils.BiometricUtils
import com.bangkit2024.core.utils.ext.showToast
import com.bangkit2024.moviesubmissionexpert.databinding.ActivityOverviewBinding
import com.bangkit2024.moviesubmissionexpert.ui.success.SuccessActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.biometric.BiometricPrompt

@AndroidEntryPoint
class OverviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOverviewBinding
    private val viewModel: OverviewViewModel by viewModels()

    private var idMovie: Int = 0
    private lateinit var title: String
    private lateinit var posterPath: String
    private lateinit var selectedSeats: String
    private lateinit var selectedDate: String
    private lateinit var selectedDay: String
    private lateinit var selectedTime: String
    private var duration: Int? = 0
    private var totalSeat: Int = 0
    private var price: Int = 0

    private val serviceFee: Int = 2_000

    private val dummyPromoList: Map<String, Int> = mapOf(
        "AYONONTON" to 20_000,
        "SKUYNONTON" to 30_000,
        "DICODING" to 40_000
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOverviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupOnBackPressed()

        idMovie = intent.getIntExtra(EXTRA_ID_MOVIE, 0)
        viewModel.getDetailMovie(idMovie)

        selectedSeats = intent.getStringExtra(EXTRA_SELECTED_SEATS).toString()
        selectedDate = intent.getStringExtra(EXTRA_DATE).toString()
        selectedDay = intent.getStringExtra(EXTRA_DAY).toString()
        selectedTime = intent.getStringExtra(EXTRA_TIME).toString()
        totalSeat = intent.getIntExtra(EXTRA_TOTAL_SEAT, 0)
        price = intent.getIntExtra(EXTRA_PRICE, 0)

        viewModel.getDetailMovie(idMovie).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.skeletonOverview.showSkeleton()
                    }

                    is Result.Success -> {
                        binding.skeletonOverview.showOriginal()

                        val dataOverview = result.data
                        setupData(dataOverview)
                    }

                    is Result.Error -> {
                        binding.skeletonOverview.showOriginal()
                        showToast(result.error)
                    }
                }
            }
        }

        binding.etPromo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (dummyPromoList.keys.any { it == text.toString() }) {
                    setupPromo(text)
                    binding.etPromo.setText(getString(R.string.null_text))
                    binding.etPromo.isEnabled = false
                    binding.etPromo.hint = "Promo $text applied"
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.btnContinue.setOnClickListener {
            if (BiometricUtils.isBiometricReady(this)) {
                BiometricUtils.showBiometricPrompt(
                    activity = this,
                    listener = object : BiometricAuthListener {
                        override fun onBiometricAuthenticateError(error: Int, errMsg: String) {
                            if (error == BiometricPrompt.ERROR_USER_CANCELED) {
                                showToast("Transaction Cancelled")
                            } else {
                                showToast(errMsg)
                            }
                        }

                        override fun onBiometricAuthenticateFailed() {
                            Log.d("Overview", "Wah ternyata masuk sini jika biometrik gagal")
                        }

                        override fun onBiometricAuthenticateSuccess(result: BiometricPrompt.AuthenticationResult) {
                            // Authentication Type (result.authenticationType)
                            // 1 -> sandi
                            // 2 -> Biometric
                            moveIntoSuccessWithData()
                        }

                    },
                    cryptoObject = null
                )
            } else {
                showToast("No biometric support. You can use sandi")
            }
        }
    }

    private fun moveIntoSuccessWithData() {
        val movieTicket = MovieTicket(
            idTicket = GenerateTicketCode.generateTicketCode(
                idMovie = idMovie,
                day = selectedDay,
                date = selectedDate,
                time = selectedTime,
                selectedSeats = selectedSeats
            ),
            idMovie = idMovie,
            titleMovie = title,
            imageMovie = posterPath,
            rating = binding.tvRating.text.toString(),
            duration = duration,
            date = "$selectedDay, $selectedDate",
            time = selectedTime,
            seat = selectedSeats,
            totalSeat = totalSeat,
            isOnReminder = true,
            isWatched = false
        )
        viewModel.insertMovieTicket(movieTicket)
        val intentToSuccess = Intent(this, SuccessActivity::class.java)

        intentToSuccess.putExtra(SuccessActivity.EXTRA_ID_TICKET, movieTicket.idTicket)

        startActivity(intentToSuccess)
    }

    private fun setupData(data: DetailMovie) {
        with(binding) {
            posterPath = data.posterPath.toString()
            Glide.with(this@OverviewActivity)
                .load(BuildConfig.IMAGE_URL + data.posterPath)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivPosterBookingSummary)

            title = data.originalTitle.toString()
            tvOverviewTitle.text = title

            tvRating.text = getRating(data.adult)
            tvSkor.text = data.popularity.toString()

            duration = data.runtime
            val durationMovie = "$duration Minutes"
            tvDuration.text = durationMovie

            tvSeat.text = selectedSeats

            val date = "$selectedDay, $selectedDate"
            tvDate.text = date

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val timeFormat = LocalTime.parse(selectedTime, DateTimeFormatter.ofPattern("HH:mm"))
                val endMovieTime = timeFormat.plusMinutes(duration?.toLong() ?: 0)
                tvTime.text = getString(R.string.interval_time_movie, selectedTime, endMovieTime.toString())
            }

            val ticketPrice = "Rp$price x $totalSeat"
            tvTicketPrice.text = ticketPrice

            val serviceFeeText = "Rp$serviceFee x $totalSeat"
            tvServiceFee.text = serviceFeeText

            val totalPrice = "Rp${(price * totalSeat) + (serviceFee * totalSeat)}"
            tvTotalPrice.text = totalPrice
        }
    }

    private fun setupPromo(textPromo: CharSequence?) {
        with(binding) {
            tvLabelPromoVoucher.text = getString(R.string.promo_discount_label, textPromo)
            tvLabelPromoVoucher.visibility = View.VISIBLE

            tvPromo.text =
                getString(R.string.promo_discount, dummyPromoList[textPromo.toString()].toString())
            tvPromo.visibility = View.VISIBLE

            val currentTotalPrice = tvTotalPrice.text.toString().substring(2).toInt()
            val totalPromo = dummyPromoList[textPromo.toString()].toString().toInt()
            Log.d("OverviewActivity", currentTotalPrice.toString())
            Log.d("OverviewActivity", totalPromo.toString())
            val totalPriceAfterPromoText = "Rp${currentTotalPrice - totalPromo}"
            tvTotalPrice.text = totalPriceAfterPromoText
        }
    }

    private fun getRating(rating: Boolean?): String =
        if (rating == true) "17+" else "13+"

    private fun setupOnBackPressed() {
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        const val EXTRA_SELECTED_SEATS = "extra_selected_seats"
        const val EXTRA_ID_MOVIE = "extra_id_movie"
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_DAY = "extra_day"
        const val EXTRA_TIME = "extra_time"
        const val EXTRA_TOTAL_SEAT = "extra_total_seat"
        const val EXTRA_PRICE = "extra_price"
    }
}