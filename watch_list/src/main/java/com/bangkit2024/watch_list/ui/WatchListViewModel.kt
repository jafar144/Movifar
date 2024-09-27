package com.bangkit2024.watch_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit2024.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class WatchListViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    fun getWatchListMovie() = movieUseCase.getAllWatchList().asLiveData()
}