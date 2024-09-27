package com.bangkit2024.core.domain.model

data class MovieTicket(
    var idTicket: String,
    var idMovie: Int?,
    var titleMovie: String?,
    var imageMovie: String?,
    var rating: String?,
    var duration: Int?,
    var date: String?,
    var time: String?,
    var seat: String?,
    var totalSeat: Int?,
    var isWatched: Boolean = false,
    var isOnReminder: Boolean = true
)
