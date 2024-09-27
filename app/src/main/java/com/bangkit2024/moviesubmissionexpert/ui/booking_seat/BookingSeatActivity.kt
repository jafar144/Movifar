package com.bangkit2024.moviesubmissionexpert.ui.booking_seat

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.core.utils.ext.addDateChip
import com.bangkit2024.core.utils.ext.addTimeChip
import com.bangkit2024.moviesubmissionexpert.R
import com.bangkit2024.moviesubmissionexpert.databinding.ActivityBookingSeatScreenBinding
import com.bangkit2024.moviesubmissionexpert.ui.detail.DetailActivity
import com.bangkit2024.moviesubmissionexpert.ui.overview.OverviewActivity
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingSeatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingSeatScreenBinding
    private val viewModel: BookingSeatViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookingSeatScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupOnBackPressed()

        val movieId = intent.getIntExtra(DetailActivity.EXTRA_ID_MOVIE, 0)

        setupDateAndTimeChip()

        binding.seatView.setViewModel(viewModel)

        viewModel.totalSelectedSeats.observe(this) { totalBooked ->
            val totalSeats = getString(R.string.total_seats, totalBooked)
            binding.tvTotalBooking.text = totalSeats

            val totalPrice = "Rp${totalBooked * viewModel.seatPrice}"
            binding.tvTotalPrice.text = totalPrice
        }

        binding.cgDateAvailable.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                viewModel.selectedDate = checkedIds[0]
                viewModel.updateButtonState()
            } else {
                viewModel.selectedDate = null
                viewModel.updateButtonState()
            }
        }

        binding.cgTimeAvailable.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                viewModel.selectedTime = checkedIds[0]
                viewModel.updateButtonState()
            } else {
                viewModel.selectedTime = null
                viewModel.updateButtonState()
            }
        }

        viewModel.buttonState.observe(this) { isEnabled ->
            binding.btnContinue.isEnabled = isEnabled
        }

        binding.btnContinue.setOnClickListener {
            val intentToOverview = Intent(this, OverviewActivity::class.java)

            val listSeatStr = viewModel.seats.filter { it.isBooked }.joinToString { it.name }
            val totalSeatsSelected = viewModel.seats.count { it.isBooked }

            val dateSelectedId = binding.cgDateAvailable.checkedChipId
            val selectedChipText = findViewById<Chip>(dateSelectedId).text.toString().split(", ")

            val dateSelected = selectedChipText[1]
            val daySelected = selectedChipText[0]

            val timeSelectedId = binding.cgTimeAvailable.checkedChipId
            val timeSelected = findViewById<Chip>(timeSelectedId).text.toString()

            intentToOverview.putExtra(OverviewActivity.EXTRA_SELECTED_SEATS, listSeatStr)
            intentToOverview.putExtra(OverviewActivity.EXTRA_ID_MOVIE, movieId)
            intentToOverview.putExtra(OverviewActivity.EXTRA_DATE, dateSelected)
            intentToOverview.putExtra(OverviewActivity.EXTRA_DAY, daySelected)
            intentToOverview.putExtra(OverviewActivity.EXTRA_TIME, timeSelected)
            intentToOverview.putExtra(OverviewActivity.EXTRA_TOTAL_SEAT, totalSeatsSelected)
            intentToOverview.putExtra(OverviewActivity.EXTRA_PRICE, viewModel.seatPrice)

            startActivity(intentToOverview)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupDateAndTimeChip() {
        viewModel.dateAvailable.forEach { date ->
            binding.cgDateAvailable.addDateChip(
                context = this,
                day = date.day,
                date = date.date,
                month = date.month,
                year = date.year
            )
        }

        viewModel.timeAvailable.forEach { time ->
            binding.cgTimeAvailable.addTimeChip(this, time.time)
        }
    }

    private fun setupOnBackPressed() {
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        onBackPressedDispatcher.addCallback(this) {
            finish()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                overrideActivityTransition(
                    OVERRIDE_TRANSITION_OPEN,
                    R.anim.no_animation,
                    R.anim.slide_out_up
                )
            } else {
                overridePendingTransition(R.anim.no_animation, R.anim.slide_out_up)
            }
        }
    }

    companion object {
        const val EXTRA_ID_MOVIE = "extra_id_movie"
    }
}