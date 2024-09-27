package com.bangkit2024.moviesubmissionexpert.ui.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit2024.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    fun getUpcomingMovieTicket() = movieUseCase.getUpcomingMovieTicket().asLiveData()

    fun getWatchedMovieTicket() = movieUseCase.getWatchedMovieTicket().asLiveData()

    fun changeIsOnReminderTicket(
        idTicket: String, isOnReminder: Boolean
    ) = viewModelScope.launch {
        movieUseCase.changeIsOnReminderTicket(idTicket, isOnReminder)
    }
}