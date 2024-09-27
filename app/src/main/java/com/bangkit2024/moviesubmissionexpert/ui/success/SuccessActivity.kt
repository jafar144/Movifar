package com.bangkit2024.moviesubmissionexpert.ui.success

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bangkit2024.moviesubmissionexpert.R
import com.bangkit2024.moviesubmissionexpert.ui.ticket.detail.DetailTicketActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuccessActivity : AppCompatActivity() {

    private var idTicket: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        if (!intent.hasExtra(EXTRA_ID_TICKET)) {
            finish()
        }

        idTicket = intent.getStringExtra(EXTRA_ID_TICKET)

        lifecycleScope.launch {
            delay(1700)

            val intentToDetailTicket = Intent(this@SuccessActivity, DetailTicketActivity::class.java)
            intentToDetailTicket.putExtra(DetailTicketActivity.EXTRA_ID_TICKET, idTicket)
            intentToDetailTicket.putExtra(DetailTicketActivity.EXTRA_FROM, DetailTicketActivity.FROM_SUCCESS)
            intentToDetailTicket.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

            startActivity(intentToDetailTicket)
        }
    }

    companion object {
        const val EXTRA_ID_TICKET = "extra_id_ticket"
    }
}