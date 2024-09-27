package com.bangkit2024.moviesubmissionexpert.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit2024.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    fun getNowPlayingMovie() = movieUseCase.getNowPlayingMovies().asLiveData()
    fun getUpcomingMovie() = movieUseCase.getUpcomingMovies().asLiveData()
}