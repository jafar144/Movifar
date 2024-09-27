package com.bangkit2024.moviesubmissionexpert.ui.custom_view

data class Seat(
    val id: String,
    var x: Float? = 0f,
    var y: Float? = 0f,
    val name: String,
    var isBooked: Boolean
)