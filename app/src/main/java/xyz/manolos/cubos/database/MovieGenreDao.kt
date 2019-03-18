package xyz.manolos.cubos.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.reactivex.Completable
import xyz.manolos.cubos.model.Movie
import xyz.manolos.cubos.model.MovieGenre

@Dao
interface MovieGenreDao {

    @Query("select movie.* from movie_genre inner join movie on movie_genre.id_movie = movie.id where movie_genre.id_genre == :genre_id")
    fun getAllMoviesByGenreId(genre_id: Long): LiveData<List<Movie>>

    @Insert(onConflict = REPLACE)
    fun insertMoviesGenres(moviesGenres: List<MovieGenre>): Completable
}