package xyz.manolos.cubos.search

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import xyz.manolos.cubos.model.ResponseMovies
import xyz.manolos.cubos.service.MovieService
import javax.inject.Inject

class SearchResultsPresenter @Inject constructor(
    private val view: SearchResultsView,
    private val movieService: MovieService
) {

    private var query: String = ""
    private val disposables = CompositeDisposable()

    fun fetchMoviesByText(page: Int, query: String) {
        view.showLoading()
        this.query = query
        movieService.fetchMoviesByQuery(page, query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    resetPageAndHideLoading(it)
                },
                onError = {
                    showErrorAndHideLoading()
                }
            )
            .addTo(disposables)
    }

    fun fetchNextPage(page: Int) {
        view.showLoading()
        movieService.fetchMoviesByQuery(page, query)
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

    private fun resetPageAndHideLoading(it: ResponseMovies) {
        view.resetPage(it)
        view.hideLoading()
    }

    private fun showErrorAndHideLoading() {
        view.showError()
        view.hideLoading()
    }
}