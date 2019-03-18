package xyz.manolos.cubos.movie

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import xyz.manolos.cubos.model.ResponseMovies
import xyz.manolos.cubos.service.MovieService
import javax.inject.Inject

class MoviePresenter @Inject constructor(
    private val view: MovieView,
    private val movieService: MovieService
) {

    private val disposables = CompositeDisposable()

    fun fetchMovies(page: Int, genreId: Int) {
        movieService.fetchMoviesByGenreId(page, genreId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    updatePageAndHideLoading(it)
                },
                onError = {
                    showErrorAndHideLoading()
                }
            )
            .addTo(disposables)
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