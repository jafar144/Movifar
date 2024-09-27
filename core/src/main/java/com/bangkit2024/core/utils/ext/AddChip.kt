package com.bangkit2024.core.utils.ext

import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import com.bangkit2024.core.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.shape.ShapeAppearanceModel
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

fun ChipGroup.addDateChip(
    context: Context,
    day: String,
    date: Int,
    month: Int,
    year: Int
) {
    val currentDate = Calendar.getInstance()

    val chipDate = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, date)
        set(Calendar.MONTH, month)
        set(Calendar.YEAR, year)
    }

    Chip(context).apply {
        id = View.generateViewId()
        text = context.getString(
            R.string.chip_date,
            day,
            date,
            getThreeCharMonth(month)
        )
        textSize = 13F
        setTextColor(context.getColor(R.color.white))
        chipStrokeColor = context.getColorStateList(R.color.chip_border)
        isCheckable = true
        chipMinHeight = 110F
        typeface = ResourcesCompat.getFont(context, R.font.open_sans_semi_bold)
        textAlignment = View.TEXT_ALIGNMENT_CENTER
        isCheckedIconVisible = false
        shapeAppearanceModel = ShapeAppearanceModel.Builder()
            .setAllCornerSizes(12F)
            .build()
        chipBackgroundColor = context.getColorStateList(R.color.chip_bg)

        // if chipDate is before and equal to currentDate, then disable the chip
        isEnabled = chipDate.equals(currentDate) or chipDate.after(currentDate)

        addView(this)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun ChipGroup.addTimeChip(
    context: Context,
    time: String
) {
    val currentTime = LocalTime.now(ZoneId.of("Asia/Jakarta"))
    val chipTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))

    Chip(context).apply {
        id = View.generateViewId()
        text = time
        textSize = 13F
        setTextColor(context.getColor(R.color.white))
        chipStrokeColor = context.getColorStateList(R.color.chip_border)
        isCheckable = true
        chipMinHeight = 110F
        typeface = ResourcesCompat.getFont(context, R.font.open_sans_semi_bold)
        textAlignment = View.TEXT_ALIGNMENT_CENTER
        isCheckedIconVisible = false
        shapeAppearanceModel = ShapeAppearanceModel.Builder()
            .setAllCornerSizes(12F)
            .build()
        chipBackgroundColor = context.getColorStateList(R.color.chip_bg)

        // if current date is before today, then disable the chip
        isEnabled = chipTime.isAfter(currentTime)

        addView(this)
    }
}

private fun getThreeCharMonth(month: Int): String? {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.MONTH, month)
    }
    return calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
}