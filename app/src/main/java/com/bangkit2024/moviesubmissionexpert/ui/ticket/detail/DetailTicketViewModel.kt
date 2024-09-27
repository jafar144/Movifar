package com.bangkit2024.moviesubmissionexpert.ui.ticket.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit2024.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailTicketViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {
    fun getDetailTicket(idTicket: String) = movieUseCase.getDetailMovieTicket(idTicket).asLiveData()
}