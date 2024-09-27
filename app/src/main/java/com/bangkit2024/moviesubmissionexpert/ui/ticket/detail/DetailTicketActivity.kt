package com.bangkit2024.moviesubmissionexpert.ui.ticket.detail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.core.domain.model.MovieTicket
import com.bangkit2024.core.utils.ext.showToast
import com.bangkit2024.moviesubmissionexpert.R
import com.bangkit2024.moviesubmissionexpert.databinding.ActivityDetailTicketBinding
import com.bangkit2024.moviesubmissionexpert.ui.MainActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTicketActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTicketBinding
    private val detailTicketViewModel: DetailTicketViewModel by viewModels()

    private lateinit var idTicket: String

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                showToast("Permission Granted")
            } else {
                showToast("Permission Denied")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fromInfo = intent.getStringExtra(EXTRA_FROM).toString()

        if (fromInfo == FROM_ITEM_TICKET) {
            binding.ivExit.setImageResource(R.drawable.back)
            setupOnBackPressed(false)
        } else {
            binding.ivExit.setImageResource(R.drawable.exit)
            setupOnBackPressed(true)
        }

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        if (!intent.hasExtra(EXTRA_ID_TICKET)) {
            finish()
        }

        idTicket = intent.getStringExtra(EXTRA_ID_TICKET).toString()

        genBarCode(idTicket)
        detailTicketViewModel.getDetailTicket(idTicket).observe(this) { ticket ->
            setupIntentData(ticket)
        }
    }

    private fun setupIntentData(ticket: MovieTicket) {
        binding.tvTitleMovieTicket.text = ticket.titleMovie
        binding.tvDurationMovieTicket.text = getString(R.string.duration_minutes, ticket.duration)
        binding.tvRatingMovieTicket.text = getString(R.string.rating_ticket, ticket.rating)
        val dateTicketStr = "${setupDate(ticket.date)} 2024"
        binding.tvDateTicket.text = dateTicketStr
        binding.tvTimeTicket.text = setupTime(ticket.time)
        binding.tvSeatTicket.text = setupSeat(ticket.seat)
        binding.totalSeatTicket.text = getString(R.string.total_person_seats, ticket.totalSeat)
    }

    private fun genBarCode(title: String?) {
        val mwriter = MultiFormatWriter()
        val width = 220
        val height = 100

        try {
            val matrix = mwriter.encode(title, BarcodeFormat.CODE_128, width, height)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (i in 0 until width) {
                for (j in 0 until height) {
                    bitmap.setPixel(i, j, if (matrix[i, j]) Color.BLACK else Color.WHITE)
                }
            }
            binding.ivBarcodeTicket.setImageBitmap(bitmap)
        } catch (e: Exception) {
            showToast("Exception $e")
        }
    }

    private fun setupTime(time: String?): String {
        val minutes = time?.split(":")?.get(1)
        val newHour = time?.split(":")?.get(0)?.toInt()?.plus(2)
        return "$time - $newHour:$minutes"
    }

    private fun setupSeat(seats: String?): String {
        return seats.toString().replace(",", "   ")
    }

    private fun setupDate(date: String?) = date?.substring(5)

    private fun setupOnBackPressed(isFromSuccess: Boolean) {
        if (isFromSuccess) {
            binding.ivExit.setOnClickListener {
                val intentToHome = Intent(this, MainActivity::class.java)
                intentToHome.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intentToHome)
            }
            onBackPressedDispatcher.addCallback(this) {
                val intentToHome = Intent(this@DetailTicketActivity, MainActivity::class.java)
                intentToHome.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intentToHome)
                finish()
            }
        } else {
            binding.ivExit.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    companion object {
        const val EXTRA_ID_TICKET = "extra_id_ticket"
        const val EXTRA_FROM = "extra_from"

        const val FROM_ITEM_TICKET = "from_item_ticket"
        const val FROM_SUCCESS = "from_success"
    }
}