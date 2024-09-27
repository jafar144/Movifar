package com.bangkit2024.core.domain.usecase

import com.bangkit2024.core.data.repository.MovieRepository
import com.bangkit2024.core.domain.model.DetailMovie
import com.bangkit2024.core.domain.model.ItemSearch
import com.bangkit2024.core.domain.model.MovieItem
import com.bangkit2024.core.domain.model.MovieTicket
import com.bangkit2024.core.domain.model.WatchList
import kotlinx.coroutines.flow.Flow
import com.bangkit2024.core.data.source.remote.retrofit.Result
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieRepository: MovieRepository
) : MovieUseCase {

    override fun getNowPlayingMovies(): Flow<Result<List<MovieItem?>>> =
        movieRepository.getNowPlayingMovies()

    override fun getUpcomingMovies(): Flow<Result<List<MovieItem?>>> =
        movieRepository.getUpcomingMovies()

    override fun getDetailNowPlayingMovies(movieId: Int): Flow<Result<DetailMovie>> =
        movieRepository.getDetailNowPlayingMovies(movieId)

    override fun searchMovie(query: String): Flow<Result<List<ItemSearch?>>> =
        movieRepository.searchMovie(query)

    override fun insertMovieTicket(movieTicket: MovieTicket) =
        movieRepository.insertMovieTicket(movieTicket)

    override fun getDetailMovieTicket(idTicket: String): Flow<MovieTicket> =
        movieRepository.getDetailMovieTicket(idTicket)

    override fun getUpcomingMovieTicket(): Flow<List<MovieTicket>> =
        movieRepository.getUpcomingMovieTicket()

    override fun getWatchedMovieTicket(): Flow<List<MovieTicket>> =
        movieRepository.getWatchedMovieTicket()

    override fun changeIsOnReminderTicket(idTicket: String, isOnReminder: Boolean) =
        movieRepository.changeIsOnReminderTicket(idTicket, isOnReminder)

    override fun insertWatchListMovie(watchList: WatchList) =
        movieRepository.insertWatchListMovie(watchList)

    override fun deleteWatchListMovie(idMovie: Int) =
        movieRepository.deleteWatchListMovie(idMovie)

    override fun getAllWatchList(): Flow<List<WatchList>> =
        movieRepository.getAllWatchList()

    override fun getStatusWatchListMovie(id: Int): Flow<Boolean> =
        movieRepository.getStatusWatchListMovie(id)
}