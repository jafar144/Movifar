package com.bangkit2024.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit2024.core.data.source.local.entity.MovieTicketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieTicketDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieTicket(movieTicketEntity: MovieTicketEntity)

    // Get Upcoming / isWatched = 0
    @Query("SELECT * FROM MovieTicketEntity WHERE isWatched = 0")
    fun getUpcomingMovieTicket(): Flow<List<MovieTicketEntity>>

    // Get Watched / isWatched = 1
    @Query("SELECT * FROM MovieTicketEntity WHERE isWatched = 1")
    fun getWatchedMovieTicket(): Flow<List<MovieTicketEntity>>

    // Change isOnReminder
    @Query("UPDATE MovieTicketEntity SET isOnReminder = :isOnReminder WHERE idTicket = :idTicket")
    suspend fun changeIsOnReminderTicket(idTicket: String, isOnReminder: Boolean)

    // Get Detail Movie Ticket
    @Query("SELECT * FROM MovieTicketEntity WHERE idTicket = :idTicket")
    fun getDetailMovieTicket(idTicket: String): Flow<MovieTicketEntity>

}