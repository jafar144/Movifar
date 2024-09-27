package com.bangkit2024.core.data.source.remote.retrofit

import com.bangkit2024.core.data.source.remote.response.DetailMovieResponse
import com.bangkit2024.core.data.source.remote.response.MovieResponse
import com.bangkit2024.core.data.source.remote.response.SearchMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Now Playing Movie
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovie(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : MovieResponse

    // Upcoming Movie
    @GET("movie/upcoming")
    suspend fun getUpComingMovie(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : MovieResponse

    // Detail Now Playing Movie
    @GET("movie/{movie_id}")
    suspend fun getDetailNowPlayingMovie(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") appendToResponse: String = "credits",
        @Query("language") language: String = "en-US"
    ) : DetailMovieResponse

    // Search Movie By Name
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("primary_release_year") primaryReleaseYear: Int = 2024,
        @Query("year") year: Int = 2024
    ) : SearchMovieResponse

}