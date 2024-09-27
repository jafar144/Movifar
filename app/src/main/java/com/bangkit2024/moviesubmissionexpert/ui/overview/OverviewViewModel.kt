package com.bangkit2024.moviesubmissionexpert.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit2024.core.domain.model.MovieTicket
import com.bangkit2024.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    fun getDetailMovie(movieId: Int) = movieUseCase.getDetailNowPlayingMovies(movieId).asLiveData()

    fun insertMovieTicket(movieTicket: MovieTicket) =
        movieUseCase.insertMovieTicket(movieTicket)
    
}