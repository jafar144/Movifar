package com.bangkit2024.core.di

import android.content.Context
import android.os.Build
import androidx.room.Room
import com.bangkit2024.core.data.source.local.room.MovieTicketRoomDatabase
import com.bangkit2024.core.utils.Contants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import java.security.SecureRandom
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieTicketRoomDatabase = Room.databaseBuilder(
        context,
        MovieTicketRoomDatabase::class.java, "MovieTicket.db"
    ).fallbackToDestructiveMigration()
        .openHelperFactory(SupportFactory(SQLiteDatabase.getBytes((Contants.KEY_DATABASE).toCharArray())))
        .build()

    @Provides
    fun provideMovieTicketDao(database: MovieTicketRoomDatabase) = database.movieTicketDao()

    @Provides
    fun provideWatchListMovieDao(database: MovieTicketRoomDatabase) = database.watchListMovieDao()

    @Provides
    fun provideDatabaseCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)
}