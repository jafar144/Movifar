package com.bangkit2024.core.domain.model

import com.bangkit2024.core.data.source.remote.response.GenresItem

data class DetailMovie(
    val originalLanguage: String?,
    val imdbId: String?,
    val video: Boolean?,
    val title: String?,
    val backdropPath: String?,
    val revenue: Int?,
    val credits: CreditsDomain?,
    val genres: List<GenresItem?>?,
    val popularity: Double?,
    val id: Int?,
    val voteCount: Int?,
    val budget: Int?,
    val overview: String?,
    val originalTitle: String?,
    val runtime: Int?,
    val posterPath: String?,
    val originCountry: List<String?>?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val tagline: String?,
    val adult: Boolean?,
    val homepage: String?,
    val status: String?
)

data class CreditsDomain(
    val cast: List<CastItemDomain?>?,
    val crew: List<CrewItemDomain?>?
)

data class CastItemDomain(
    val castId: Int?,
    val character: String?,
    val gender: Int?,
    val creditId: String?,
    val knownForDepartment: String?,
    val originalName: String?,
    val popularity: Double?,
    val name: String?,
    val profilePath: String?,
    val id: Int?,
    val adult: Boolean?,
    val order: Int?
)

data class CrewItemDomain(
    val gender: Int?,
    val creditId: String?,
    val knownForDepartment: String?,
    val originalName: String?,
    val popularity: Double?,
    val name: String?,
    val profilePath: String?,
    val id: Int?,
    val adult: Boolean?,
    val department: String?,
    val job: String?
)