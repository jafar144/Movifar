package com.bangkit2024.core.data.source.local

import com.bangkit2024.core.data.source.local.entity.MovieTicketEntity
import com.bangkit2024.core.data.source.local.entity.WatchListMovie
import com.bangkit2024.core.data.source.local.room.MovieTicketDao
import com.bangkit2024.core.data.source.local.room.WatchListMovieDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val movieTicketDao: MovieTicketDao,
    private val watchListMovieDao: WatchListMovieDao
){
    // ------------------------------------- Movie Ticket ------------------------------------------
    suspend fun insertMovieTicket(movieTicketEntity: MovieTicketEntity) =
        movieTicketDao.insertMovieTicket(movieTicketEntity)

    fun getUpcomingMovieTicket() = movieTicketDao.getUpcomingMovieTicket()

    fun getWatchedMovieTicket() = movieTicketDao.getWatchedMovieTicket()

    suspend fun changeIsOnReminderTicket(
        idTicket: String, isOnReminder: Boolean
    ) = movieTicketDao.changeIsOnReminderTicket(idTicket, isOnReminder)

    fun getDetailMovieTicket(idTicket: String) = movieTicketDao.getDetailMovieTicket(idTicket)


    // ------------------------------------- Watch List Movie  ------------------------------------------
    suspend fun insertWatchListMovie(watchListMovie: WatchListMovie) =
        watchListMovieDao.insertFavorite(watchListMovie)

    suspend fun deleteWatchListMovie(idMovie: Int) =
        watchListMovieDao.deleteFavorite(idMovie)

    fun getAllWatchList() = watchListMovieDao.getAllFavorite()

    fun getStatusWatchListMovie(id: Int) = watchListMovieDao.isFavorite(id)
}