package com.bangkit2024.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bangkit2024.core.data.source.local.entity.MovieTicketEntity
import com.bangkit2024.core.data.source.local.entity.WatchListMovie

@Database(
    entities = [MovieTicketEntity::class, WatchListMovie::class],
    version = 1,
    exportSchema = false
)
abstract class MovieTicketRoomDatabase : RoomDatabase() {
    abstract fun movieTicketDao(): MovieTicketDao
    abstract fun watchListMovieDao(): WatchListMovieDao
}