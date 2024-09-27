package com.bangkit2024.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieTicketEntity(
    // idTicket
    @PrimaryKey
    @ColumnInfo(name = "idTicket")
    var idTicket: String,

    // idMovie
    @ColumnInfo(name = "idMovie")
    var idMovie: Int? = null,

    // titleMovie
    @ColumnInfo(name = "titleMovie")
    var titleMovie: String? = null,

    // imageMovie
    @ColumnInfo(name = "imageMovie")
    var imageMovie: String? = null,

    // rating
    @ColumnInfo(name = "rating")
    var rating: String? = null,

    // duration
    @ColumnInfo(name = "duration")
    var duration: Int? = null,

    // date
    @ColumnInfo(name = "date")
    var date: String? = null,

    // time
    @ColumnInfo(name = "time")
    var time: String? = null,

    // seat
    @ColumnInfo(name = "seat")
    var seat: String? = null,

    // totalSeat
    @ColumnInfo(name = "totalSeat")
    var totalSeat: Int? = null,

    // isWatched
    @ColumnInfo(name = "isWatched")
    var isWatched: Boolean = false,

    // isOnReminder
    @ColumnInfo(name = "isOnReminder")
    var isOnReminder: Boolean = false

)