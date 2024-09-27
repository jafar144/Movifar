package com.bangkit2024.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WatchListMovie(
    // idMovie
    @PrimaryKey
    @ColumnInfo(name = "idMovie")
    var idMovie: Int? = null,

    // imageMovie
    @ColumnInfo(name = "imageMovie")
    var imageMovie: String? = null,

    // titleMovie
    @ColumnInfo(name = "titleMovie")
    var titleMovie: String? = null,

)