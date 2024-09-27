package com.bangkit2024.moviesubmissionexpert.di

import com.bangkit2024.core.domain.usecase.MovieUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WatchListModuleDependencies {
    fun provideMovieUseCase(): MovieUseCase
}