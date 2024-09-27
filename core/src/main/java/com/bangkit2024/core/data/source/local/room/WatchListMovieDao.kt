package com.bangkit2024.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit2024.core.data.source.local.entity.WatchListMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchListMovieDao {

    // Get all favorite
    @Query("SELECT * FROM WatchListMovie")
    fun getAllFavorite(): Flow<List<WatchListMovie>>

    // Check if the id is in the table
    @Query("SELECT EXISTS(SELECT * FROM WatchListMovie WHERE idMovie = :idMovie)")
    fun isFavorite(idMovie: Int): Flow<Boolean>

    // Insert to the table
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(watchListMovie: WatchListMovie)

    @Query("DELETE FROM WatchListMovie WHERE idMovie = :idMovie")
    suspend fun deleteFavorite(idMovie: Int)

}