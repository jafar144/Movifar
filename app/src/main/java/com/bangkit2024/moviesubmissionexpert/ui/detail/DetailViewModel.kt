package com.bangkit2024.moviesubmissionexpert.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit2024.core.domain.model.WatchList
import com.bangkit2024.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    fun getDetailMovie(movieId: Int) = movieUseCase.getDetailNowPlayingMovies(movieId).asLiveData()

    fun insertWatchListMovie(watchListMovie: WatchList) =
        movieUseCase.insertWatchListMovie(watchListMovie)

    fun deleteWatchListMovie(idMovie: Int) =
        movieUseCase.deleteWatchListMovie(idMovie)

    fun getStatusWatchListMovie(id: Int) =
        movieUseCase.getStatusWatchListMovie(id).asLiveData()

}

