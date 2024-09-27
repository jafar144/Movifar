package com.bangkit2024.moviesubmissionexpert.ui.custom_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.bangkit2024.moviesubmissionexpert.R
import com.bangkit2024.moviesubmissionexpert.ui.booking_seat.BookingSeatViewModel

class SeatView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val backgroundSeat = Paint()
    private val strokePaintSeat = Paint()
    private val numberSeat = Paint()
    private val mBounds = Rect()

    private val widthSeat = 80F
    private val heightSeat = 80F
    private val spaceRow = 100F
    private val spaceColumn = 6F

    private val marginStartX = 40F
    private val marginStartY = 0F
    private val radiusSeat: FloatArray = FloatArray(8) { 10F }

    private lateinit var bookingSeatViewModel: BookingSeatViewModel

    fun setViewModel(viewModel: BookingSeatViewModel) {
        this.bookingSeatViewModel = viewModel
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)

        val totalRows = bookingSeatViewModel.seats.groupBy { it.id[0] }.size - 3
        val totalHeight = (marginStartY + (totalRows * (heightSeat + spaceRow)) - spaceRow).toInt()

        val height = resolveSizeAndState(totalHeight, heightMeasureSpec, 0)

        setMeasuredDimension(width, height)

        val halfOfWidth = width / 2
        var valueX = marginStartX
        var valueY = marginStartY
        var isReadyResetValueX = true

        var currentRow = 'A'

        for (seat in bookingSeatViewModel.seats) {
            val seatRow = seat.id[0]
            val seatNumber = seat.id.substring(1).toInt()

            if (seatRow != currentRow) {
                currentRow = seatRow
                valueY += spaceRow
                valueX = marginStartX
                isReadyResetValueX = true
            }

            if (seatNumber <= 5) {
                seat.apply {
                    x = valueX
                    y = valueY
                }
                valueX += halfOfWidth / 6 + spaceColumn
            } else {
                if (isReadyResetValueX) {
                    valueX = marginStartX
                    isReadyResetValueX = false
                }
                seat.apply {
                    x = halfOfWidth + valueX
                    y = valueY
                }
                valueX += halfOfWidth / 6 + spaceColumn
            }

        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (seat in bookingSeatViewModel.seats) {
            drawSeat(canvas, seat)
        }

    }

    private fun drawSeat(canvas: Canvas?, seat: Seat) {
        backgroundSeat.style = Paint.Style.FILL
        strokePaintSeat.style = Paint.Style.STROKE
        strokePaintSeat.strokeWidth = 1F

        if (seat.isBooked) {
            backgroundSeat.color = ContextCompat.getColor(context, R.color.red_light)
            strokePaintSeat.color = ContextCompat.getColor(context, R.color.red_light)
            numberSeat.color = ContextCompat.getColor(context, R.color.white)
        } else {
            backgroundSeat.color = ContextCompat.getColor(context, R.color.bg_black)
            strokePaintSeat.color = ContextCompat.getColor(context, R.color.stroke_grey)
            numberSeat.color = ContextCompat.getColor(context, R.color.white)
        }

        canvas?.save()
        canvas?.translate(seat.x as Float, seat.y as Float)

        // Kursi
        val backgroundPath = Path()
        backgroundPath.addRoundRect(0F, 0F, widthSeat, heightSeat, radiusSeat, Path.Direction.CCW)
        canvas?.drawPath(backgroundPath, backgroundSeat)
        canvas?.drawPath(backgroundPath, strokePaintSeat)

        // Font Kursi
        val semiBoldSans = ResourcesCompat.getFont(context, R.font.open_sans_semi_bold)

        // Nomor Kursi
        numberSeat.apply {
            textSize = 20F
            typeface = semiBoldSans
            getTextBounds(seat.name, 0, seat.name.length, mBounds)
        }

        val centerX = widthSeat / 2
        val centerY = heightSeat / 2

        val textX = centerX - mBounds.centerX()
        val textY = centerY - mBounds.centerY()

        canvas?.drawText(seat.name, textX, textY, numberSeat)

        canvas?.restore()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val halfOfWidth = width / 2
        val startX = marginStartX
        val startY = marginStartY

        val widthColumns = Array(10) { index ->
            val isLeftSide = index < 5
            if (isLeftSide) {
                // Adjust calculation to ensure proper detection for boundary seats
                startX + (index * (halfOfWidth / 6 + spaceColumn))..startX + (index * (halfOfWidth / 6 + spaceColumn)) + widthSeat
            } else {
                halfOfWidth + (index - 5) * (halfOfWidth / 6 + spaceColumn)..halfOfWidth + (index - 5) * (halfOfWidth / 6 + spaceColumn) + widthSeat
            }
        }

        val heightRows = Array(10) { index ->
            startY + spaceRow * index..startY + spaceRow * index + heightSeat
        }

        if (event?.action == MotionEvent.ACTION_DOWN) {
            for (i in 0..9) {
                for (j in 0..9) {
                    if (event.x in widthColumns[j] && event.y in heightRows[i]) {
                        booking("${'A' + i}${j + 1}")
                        return true
                    }
                }
            }
        }

        return true
    }

    private fun booking(seat: String) {
        bookingSeatViewModel.seats.find { it.id == seat }?.apply {
            if (!isBooked) {
                if (bookingSeatViewModel.seats.count { it.isBooked } == 6) return
                isBooked = true
                bookingSeatViewModel.bookSeat(true)
            } else {
                isBooked = false
                bookingSeatViewModel.bookSeat(false)
            }
            bookingSeatViewModel.updateButtonState()
        }

        invalidate()
    }
}