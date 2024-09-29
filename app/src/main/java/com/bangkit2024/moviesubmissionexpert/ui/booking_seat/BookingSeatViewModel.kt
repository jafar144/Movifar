package com.bangkit2024.moviesubmissionexpert.ui.booking_seat

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit2024.core.data.source.DateAvailable
import com.bangkit2024.core.data.source.TimeAvailable
import com.bangkit2024.moviesubmissionexpert.ui.custom_view.Seat

class BookingSeatViewModel : ViewModel() {

    private var _totalSelectedSeats = MutableLiveData<Int>()
    val totalSelectedSeats: LiveData<Int> = _totalSelectedSeats

    private var _buttonState = MutableLiveData<Boolean>()
    val buttonState: LiveData<Boolean> = _buttonState

    var selectedDate: Int? = null
    var selectedTime: Int? = null

    var seats: List<Seat>
    val seatPrice: Int = 40_000

    val dateAvailable: List<DateAvailable>
    val timeAvailable: List<TimeAvailable>

    init {
        seats = makeAllSeats()
        dateAvailable = setDummyDateAvailable()
        timeAvailable = setDummyTimeAvailable()
    }

    fun updateButtonState() {
        _buttonState.value = selectedDate != null && selectedTime != null && (_totalSelectedSeats.value ?: 0) > 0
    }

    private fun setDummyDateAvailable(): List<DateAvailable> {
        val listDateAvailable = mutableListOf<DateAvailable>()

        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_WEEK)

        // if today is sunday, then show the next 7 days
        if (currentDay == Calendar.SUNDAY) {
            for (i in 0..6) {
                listDateAvailable.add(
                    DateAvailable(
                        day = getShortDay(calendar.get(Calendar.DAY_OF_WEEK)),
                        date = calendar.get(Calendar.DAY_OF_MONTH),
                        month = calendar.get(Calendar.MONTH),
                        year = calendar.get(Calendar.YEAR)
                    )
                )
                // move to the next day
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
        } else {
            // if today is not sunday, then show 7 days in the current week
            // move to the sunday of current week
            val daysToSunday = Calendar.SUNDAY - currentDay
            calendar.add(Calendar.DAY_OF_MONTH, daysToSunday)

            for (i in 0..6) {
                listDateAvailable.add(
                    DateAvailable(
                        day = getShortDay(calendar.get(Calendar.DAY_OF_WEEK)),
                        date = calendar.get(Calendar.DAY_OF_MONTH),
                        month = calendar.get(Calendar.MONDAY),
                        year = calendar.get(Calendar.YEAR)
                    )
                )
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        return listDateAvailable
    }

    private fun setDummyTimeAvailable(): List<TimeAvailable> {
        return listOf(
            TimeAvailable("11:50"),
            TimeAvailable("13:50"),
            TimeAvailable("15:50"),
            TimeAvailable("17:50"),
            TimeAvailable("19:50"),
            TimeAvailable("22:50"),
        )
    }

    private fun getShortDay(day: Int): String {
        return when (day) {
            Calendar.SUNDAY -> "SUN"
            Calendar.MONDAY -> "MON"
            Calendar.TUESDAY -> "TUE"
            Calendar.WEDNESDAY -> "WED"
            Calendar.THURSDAY -> "THU"
            Calendar.FRIDAY -> "FRI"
            Calendar.SATURDAY -> "SAT"
            else -> ""
        }
    }

    fun bookSeat(isBooking: Boolean) {
        val currentCount = _totalSelectedSeats.value ?: 0

        _totalSelectedSeats.value = if (isBooking) {
            currentCount + 1
        } else {
            currentCount - 1
        }
    }

    private fun makeAllSeats(): ArrayList<Seat> {
        val seats = arrayListOf<Seat>()
        for (i in 'A'..'J') {
            for (j in 1..10) {
                seats.add(Seat(id = "$i$j", name = "$i$j", isBooked = false))
            }
        }

        return seats
    }

}