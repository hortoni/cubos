package xyz.manolos.cubos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.runner.RunWith
import xyz.manolos.cubos.database.AppDatabase
import xyz.manolos.cubos.database.MovieDao
import xyz.manolos.cubos.database.MovieGenreDao
import xyz.manolos.cubos.model.Movie
import xyz.manolos.cubos.model.MovieGenre

@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)


class DatabaseRoomTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var movieDao: MovieDao

    private lateinit var movieGenreDao: MovieGenreDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        appDatabase = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
        movieDao = appDatabase.movieDao()
        movieGenreDao = appDatabase.movieGenreDao()

    }

    @After
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun shouldReturnEmptyMovieList() {
        val allMovies = movieDao.getAllMovies()
        allMovies.observeForever {
            Assert.assertTrue(it.isEmpty())
        }
    }

    @Test
    fun shouldReturnNotEmptyMovieList() {
        val list = ArrayList<Movie>()
        list.add(Movie(1, "title", null, null, null, null))
        movieDao.insertMovies(list).subscribe()
        val allMovies = movieDao.getAllMovies()
        allMovies.observeForever {
            Assert.assertFalse(it.isEmpty())
        }
    }

    @Test
    fun shouldReturn3MoviesInMoviesList() {
        val list = ArrayList<Movie>()
        list.add(Movie(1, "title1", null, null, null, null, emptyList()))
        list.add(Movie(2, "title2", null, null, null, null, emptyList()))
        list.add(Movie(3, "title3", null, null, null, null, emptyList()))

        movieDao.insertMovies(list).subscribe()
        val allMovies = movieDao.getAllMovies()
        allMovies.observeForever {
            Assert.assertTrue(it.size == 3)
        }
    }



    @Test
    fun shouldReturnMovieByGenreId() {
        val genreListFromMovie = ArrayList<Long>()
        genreListFromMovie.add(1)
        genreListFromMovie.add(2)
        val list = ArrayList<Movie>()
        list.add(Movie(1, "title1", null, null, null, null, genreListFromMovie ))
        list.add(Movie(2, "title1", null, null, null, null, genreListFromMovie ))
        movieDao.insertMovies(list).subscribe()

        val moviesGenresList = ArrayList<MovieGenre>()
        moviesGenresList.add(MovieGenre( 1, 1, 3))
        moviesGenresList.add(MovieGenre( 2, 2, 3))
        movieGenreDao.insertMoviesGenres(moviesGenresList).subscribe()

        val genresNamesList = movieGenreDao.getAllMoviesByGenreId(3)
        genresNamesList.observeForever{
            Assert.assertTrue(it.size == 2)
        }
    }

}