package com.bangkit2024.core.data.source

data class DateAvailable(
    val day: String,
    val date: Int,
    val month: Int,
    val year: Int,
    var isSelected: Boolean = false
)