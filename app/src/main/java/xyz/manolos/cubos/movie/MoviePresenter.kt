package xyz.manolos.cubos.movie

import androidx.lifecycle.LiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import xyz.manolos.cubos.database.MovieDao
import xyz.manolos.cubos.database.MovieGenreDao
import xyz.manolos.cubos.model.Movie
import xyz.manolos.cubos.model.MovieGenre
import xyz.manolos.cubos.model.ResponseMovies
import xyz.manolos.cubos.service.MovieService
import javax.inject.Inject

class MoviePresenter @Inject constructor(
    private val view: MovieView,
    private val movieService: MovieService,
    private val movieDao: MovieDao,
    private val movieGenreDao: MovieGenreDao
) {

    private val disposables = CompositeDisposable()

    fun fetchMovies(page: Int, genreId: Long) {
        movieService.fetchMoviesByGenreId(page, genreId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    saveMovies(it.results)
                    updatePageAndHideLoading(it)
                },
                onError = {
                    showErrorAndHideLoading()
                }
            )
            .addTo(disposables)
    }

    private fun saveMovies(movies: List<Movie>) {
        movieDao.insertMovies(movies)
            .subscribeOn(Schedulers.io())
            .subscribe()

        saveMoviesGenres(movies)
    }

    fun observeMoviesByGenreId(genreId: Long): LiveData<List<Movie>> {
        return movieGenreDao.getAllMoviesByGenreId(genreId)
    }

    private fun saveMoviesGenres(movies: List<Movie>) {
        movies.map {
            movieGenreDao.insertMoviesGenres(getMoviesGenresByMovie(it))
                .subscribeOn(Schedulers.io())
                .subscribe()
        }
    }

    private fun getMoviesGenresByMovie(movie: Movie): List<MovieGenre> {
        return movie.genreIds.map { MovieGenre(id_movie = movie.id, id_genre = it) }
    }

    private fun updatePageAndHideLoading(it: ResponseMovies) {
        view.updatePage(it)
        view.hideLoading()
    }

    private fun showErrorAndHideLoading() {
        view.showError()
        view.hideLoading()
    }
}