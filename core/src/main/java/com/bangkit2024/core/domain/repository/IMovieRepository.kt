package com.bangkit2024.core.domain.repository

import com.bangkit2024.core.domain.model.DetailMovie
import com.bangkit2024.core.domain.model.ItemSearch
import com.bangkit2024.core.domain.model.MovieItem
import com.bangkit2024.core.domain.model.MovieTicket
import com.bangkit2024.core.domain.model.WatchList
import com.bangkit2024.core.data.source.remote.retrofit.Result
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    // ------------------------------------- Watch Movie -------------------------------------------
    fun getNowPlayingMovies(): Flow<Result<List<MovieItem?>>>

    fun getUpcomingMovies(): Flow<Result<List<MovieItem?>>>

    fun getDetailNowPlayingMovies(movieId: Int): Flow<Result<DetailMovie>>

    fun searchMovie(query: String): Flow<Result<List<ItemSearch?>>>

    // ------------------------------------- Ticket Movie ------------------------------------------
    fun insertMovieTicket(movieTicket: MovieTicket)

    fun getDetailMovieTicket(idTicket: String): Flow<MovieTicket>

    fun getUpcomingMovieTicket(): Flow<List<MovieTicket>>

    fun getWatchedMovieTicket(): Flow<List<MovieTicket>>

    fun changeIsOnReminderTicket(idTicket: String, isOnReminder: Boolean)

    // ------------------------------------- Watch List Movie --------------------------------------
    fun insertWatchListMovie(watchList: WatchList)

    fun deleteWatchListMovie(idMovie: Int)

    fun getAllWatchList(): Flow<List<WatchList>>

    fun getStatusWatchListMovie(id: Int): Flow<Boolean>
}