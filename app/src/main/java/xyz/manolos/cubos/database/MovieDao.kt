package xyz.manolos.cubos.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.reactivex.Completable
import xyz.manolos.cubos.model.Movie

@Dao
interface MovieDao {

    @Query("select * from movie")
    fun getAllMovies(): LiveData<List<Movie>>

    @Insert(onConflict = REPLACE)
    fun insertMovies(movies: List<Movie>): Completable
}