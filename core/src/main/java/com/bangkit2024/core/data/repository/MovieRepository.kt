package com.bangkit2024.core.data.repository

import com.bangkit2024.core.data.source.local.LocalDataSource
import com.bangkit2024.core.data.source.remote.RemoteDataSource
import com.bangkit2024.core.data.source.remote.RemoteResult
import com.bangkit2024.core.domain.model.ItemSearch
import com.bangkit2024.core.domain.model.MovieItem
import com.bangkit2024.core.domain.model.MovieTicket
import com.bangkit2024.core.domain.repository.IMovieRepository
import com.bangkit2024.core.domain.model.DetailMovie
import com.bangkit2024.core.domain.model.WatchList
import com.bangkit2024.core.utils.ext.toDomain
import com.bangkit2024.core.utils.ext.toEntity
import com.bangkit2024.core.data.source.remote.retrofit.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val databaseCoroutineScope: CoroutineScope,
) : IMovieRepository {

    override fun getNowPlayingMovies(): Flow<Result<List<MovieItem?>>> =
        flow {
            emit(Result.Loading)

            when (val response = remoteDataSource.getNowPlayingMovies().first()) {
                is RemoteResult.Success -> {
                    emit(Result.Success(response.data.map {
                        it?.toDomain()
                    }))
                }

                is RemoteResult.Empty -> {
                    emit(Result.Success(listOf()))
                }

                is RemoteResult.Error -> {
                    emit(Result.Error(response.error))
                }
            }
        }

    override fun getUpcomingMovies(): Flow<Result<List<MovieItem?>>> =
        flow {
            emit(Result.Loading)

            when (val response = remoteDataSource.getUpcomingMovies().first()) {
                is RemoteResult.Success -> {
                    emit(Result.Success(response.data.map {
                        it?.toDomain()
                    }))
                }

                is RemoteResult.Empty -> {
                    emit(Result.Success(listOf()))
                }

                is RemoteResult.Error -> {
                    emit(Result.Error(response.error))
                }
            }
        }

    override fun getDetailNowPlayingMovies(movieId: Int): Flow<Result<DetailMovie>> =
        flow {
            emit(Result.Loading)

            when (val response = remoteDataSource.getDetailNowPlayingMovies(movieId).first()) {
                is RemoteResult.Success -> {
                    emit(Result.Success(response.data.toDomain()))
                }

                is RemoteResult.Empty -> {
                    emit(Result.Error("null"))
                }

                is RemoteResult.Error -> {
                    emit(Result.Error(response.error))
                }
            }
        }

    override fun searchMovie(query: String): Flow<Result<List<ItemSearch?>>> =
        flow {
            emit(Result.Loading)

            when (val response = remoteDataSource.searchMovie(query).first()) {
                is RemoteResult.Success -> {
                    emit(Result.Success(response.data.map {
                        it?.toDomain()
                    }))
                }
                is RemoteResult.Empty -> {
                    emit(Result.Success(listOf()))
                }
                is RemoteResult.Error -> {
                    emit(Result.Error(response.error))
                }
            }
        }

    override fun insertMovieTicket(movieTicket: MovieTicket) {
        databaseCoroutineScope.launch {
            localDataSource.insertMovieTicket(movieTicket.toEntity())
        }
    }

    override fun getDetailMovieTicket(idTicket: String): Flow<MovieTicket> =
        localDataSource.getDetailMovieTicket(idTicket).map {
            it.toDomain()
        }

    override fun getUpcomingMovieTicket(): Flow<List<MovieTicket>> =
        localDataSource.getUpcomingMovieTicket().map { listMovie ->
            listMovie.map {
                it.toDomain()
            }
        }

    override fun getWatchedMovieTicket(): Flow<List<MovieTicket>> =
        localDataSource.getWatchedMovieTicket().map { listMovie ->
            listMovie.map {
                it.toDomain()
            }
        }

    override fun changeIsOnReminderTicket(idTicket: String, isOnReminder: Boolean) {
        databaseCoroutineScope.launch {
            localDataSource.changeIsOnReminderTicket(idTicket, isOnReminder)
        }
    }

    override fun insertWatchListMovie(watchList: WatchList) {
        databaseCoroutineScope.launch {
            localDataSource.insertWatchListMovie(watchList.toEntity())
        }
    }

    override fun deleteWatchListMovie(idMovie: Int) {
        databaseCoroutineScope.launch {
            localDataSource.deleteWatchListMovie(idMovie)
        }
    }

    override fun getAllWatchList(): Flow<List<WatchList>> =
        localDataSource.getAllWatchList().map { watchList ->
            watchList.map {
                it.toDomain()
            }
        }

    override fun getStatusWatchListMovie(id: Int): Flow<Boolean> =
        localDataSource.getStatusWatchListMovie(id)

}