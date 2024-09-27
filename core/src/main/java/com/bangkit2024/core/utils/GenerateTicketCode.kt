package com.bangkit2024.core.utils

object GenerateTicketCode {
    fun generateTicketCode(
        idMovie: Int,
        day: String,
        date: String,
        time: String,
        selectedSeats: String
    ): String {
        val movieCode = idMovie.toString().take(3)
        val timeCode = time.replace(":", "")
        val seatCode = selectedSeats.replace(", ", "")
        return "$movieCode-$day$date$timeCode-$seatCode"
    }
}