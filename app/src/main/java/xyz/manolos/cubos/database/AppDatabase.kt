package xyz.manolos.cubos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.manolos.cubos.model.Movie
import xyz.manolos.cubos.model.MovieGenre

@Database(entities = [Movie::class, MovieGenre::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun movieGenreDao(): MovieGenreDao
}