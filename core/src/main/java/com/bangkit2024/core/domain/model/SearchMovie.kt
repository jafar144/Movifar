package com.bangkit2024.core.domain.model

data class SearchMovie(
    val page: Int? = null,
    val totalPages: Int? = null,
    val results: List<ItemSearch?>? = null,
    val totalResults: Int? = null
)

data class ItemSearch(
    val overview: String? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val video: Boolean? = null,
    val title: String? = null,
    val genreIds: List<Int?>? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String? = null,
    val popularity: Any? = null,
    val voteAverage: Any? = null,
    val id: Int? = null,
    val adult: Boolean? = null,
    val voteCount: Int? = null
)