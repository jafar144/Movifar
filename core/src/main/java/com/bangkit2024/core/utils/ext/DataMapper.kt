package com.bangkit2024.core.utils.ext

import com.bangkit2024.core.data.source.local.entity.MovieTicketEntity
import com.bangkit2024.core.data.source.local.entity.WatchListMovie
import com.bangkit2024.core.data.source.remote.response.CastItem
import com.bangkit2024.core.data.source.remote.response.Credits
import com.bangkit2024.core.data.source.remote.response.CrewItem
import com.bangkit2024.core.data.source.remote.response.DetailMovieResponse
import com.bangkit2024.core.data.source.remote.response.ResultsItem
import com.bangkit2024.core.data.source.remote.response.ResultsItemSearch
import com.bangkit2024.core.domain.model.CastItemDomain
import com.bangkit2024.core.domain.model.CreditsDomain
import com.bangkit2024.core.domain.model.CrewItemDomain
import com.bangkit2024.core.domain.model.DetailMovie
import com.bangkit2024.core.domain.model.ItemSearch
import com.bangkit2024.core.domain.model.MovieItem
import com.bangkit2024.core.domain.model.MovieTicket
import com.bangkit2024.core.domain.model.WatchList

fun ResultsItem.toDomain() =
    MovieItem(
        overview = overview,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        video = video,
        title = title,
        genreIds = genreIds,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        popularity = popularity,
        voteAverage = voteAverage,
        id = id,
        adult = adult,
        voteCount = voteCount
    )

fun DetailMovieResponse.toDomain() =
    DetailMovie(
        originalLanguage = originalLanguage,
        imdbId = imdbId,
        video = video,
        title = title,
        backdropPath = backdropPath,
        revenue = revenue,
        credits = credits?.toDomain(),
        genres = genres,
        popularity = popularity,
        id = id,
        voteCount = voteCount,
        budget = budget,
        overview = overview,
        originalTitle = originalTitle,
        runtime = runtime,
        posterPath = posterPath,
        originCountry = originCountry,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        tagline = tagline,
        adult = adult,
        homepage = homepage,
        status = status
    )

fun Credits.toDomain() =
    CreditsDomain(
        cast = cast?.map { it?.toDomain() },
        crew = crew?.map { it?.toDomain() }
    )

fun CastItem.toDomain() =
    CastItemDomain(
        castId = castId,
        character = character,
        gender = gender,
        creditId = creditId,
        knownForDepartment = knownForDepartment,
        originalName = originalName,
        popularity = popularity,
        name = name,
        profilePath = profilePath,
        id = id,
        adult = adult,
        order = order
    )

fun CrewItem.toDomain() =
    CrewItemDomain(
        gender = gender,
        creditId = creditId,
        knownForDepartment = knownForDepartment,
        originalName = originalName,
        popularity = popularity,
        name = name,
        profilePath = profilePath,
        id = id,
        adult = adult,
        department = department,
        job = job
    )

fun MovieTicket.toEntity() =
    MovieTicketEntity(
        idTicket = idTicket,
        idMovie = idMovie,
        titleMovie = titleMovie,
        imageMovie = imageMovie,
        rating = rating,
        duration = duration,
        date = date,
        time = time,
        seat = seat,
        totalSeat = totalSeat,
        isWatched = isWatched,
        isOnReminder = isOnReminder
    )

fun MovieTicketEntity.toDomain() =
    MovieTicket(
        idTicket = idTicket,
        idMovie = idMovie,
        titleMovie = titleMovie,
        imageMovie = imageMovie,
        rating = rating,
        duration = duration,
        date = date,
        time = time,
        seat = seat,
        totalSeat = totalSeat,
        isWatched = isWatched,
        isOnReminder = isOnReminder
    )

fun WatchList.toEntity() =
    WatchListMovie(
        idMovie = idMovie,
        titleMovie = titleMovie,
        imageMovie = imageMovie
    )

fun WatchListMovie.toDomain() =
    WatchList(
        idMovie = idMovie,
        titleMovie = titleMovie,
        imageMovie = imageMovie
    )

fun ResultsItemSearch.toDomain() =
    ItemSearch(
        overview = overview,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        video = video,
        title = title,
        genreIds = genreIds,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        popularity = popularity,
        voteAverage = voteAverage,
        id = id,
        adult = adult,
        voteCount = voteCount
    )