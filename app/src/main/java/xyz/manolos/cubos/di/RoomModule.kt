package xyz.manolos.cubos.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import xyz.manolos.cubos.database.AppDatabase
import javax.inject.Singleton


@Module
object RoomModule {

    @Provides
    @JvmStatic
    @Singleton
    fun providesAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "cubos-db").build()

    @Provides
    @JvmStatic
    fun providesMovieDao(database: AppDatabase) = database.movieDao()

    @Provides
    @JvmStatic
    fun providesMovieGenreDao(database: AppDatabase) = database.movieGenreDao()
}