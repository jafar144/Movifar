package com.bangkit2024.core.data.source.remote

import com.bangkit2024.core.data.source.remote.response.DetailMovieResponse
import com.bangkit2024.core.data.source.remote.response.ResultsItem
import com.bangkit2024.core.data.source.remote.response.ResultsItemSearch
import com.bangkit2024.core.data.source.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    fun getNowPlayingMovies(): Flow<RemoteResult<List<ResultsItem?>>> =
        flow {
            try {
                val nowPlayingMovies = apiService
                    .getNowPlayingMovie()
                    .results

                if (nowPlayingMovies?.isNotEmpty() == true) {
                    emit(RemoteResult.Success(nowPlayingMovies))
                } else {
                    emit(RemoteResult.Empty)
                }
            } catch (e: Exception) {
                emit(RemoteResult.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getUpcomingMovies(): Flow<RemoteResult<List<ResultsItem?>>> =
        flow {
            try {
                val upcomingMovies = apiService
                    .getUpComingMovie()
                    .results

                if (upcomingMovies?.isNotEmpty() == true) {
                    emit(RemoteResult.Success(upcomingMovies))
                } else {
                    emit(RemoteResult.Empty)
                }
            } catch (e: Exception) {
                emit(RemoteResult.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getDetailNowPlayingMovies(movieId: Int): Flow<RemoteResult<DetailMovieResponse>> =
        flow {
            try {
                val detailNowPlayingMovies = apiService
                    .getDetailNowPlayingMovie(movieId)

                if (detailNowPlayingMovies != null) {
                    emit(RemoteResult.Success(detailNowPlayingMovies))
                } else {
                    emit(RemoteResult.Empty)
                }
            } catch (e: Exception) {
                emit(RemoteResult.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun searchMovie(query: String): Flow<RemoteResult<List<ResultsItemSearch?>>> =
        flow {
            try {
                val searchMovie = apiService
                    .searchMovie(query)
                    .results

                if (searchMovie != null) {
                    emit(RemoteResult.Success(searchMovie))
                } else {
                    emit(RemoteResult.Empty)
                }
            } catch (e: Exception) {
                emit(RemoteResult.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
}