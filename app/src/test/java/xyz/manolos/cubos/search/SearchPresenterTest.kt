package xyz.manolos.cubos.search

import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import xyz.manolos.cubos.model.ResponseMovies
import xyz.manolos.cubos.service.MovieService

@RunWith(MockitoJUnitRunner::class)


class SearchPresenterTest {
    @Mock
    private lateinit var view: SearchResultsView

    @Mock
    private lateinit var movieService: MovieService

    @InjectMocks
    private lateinit var presenter: SearchResultsPresenter

    @Before
    fun `set up`() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }


    @Test
    fun `should show error when fetch movies by text fails`() {
        BDDMockito.given(movieService.fetchMoviesByQuery(1, "")).willReturn(Single.error(Throwable()))
        presenter.fetchMoviesByText(1, "")
        verify(view).showError()
    }

    @Test
    fun `should reset page when fetch movies by query is fetched`() {
        val responseMovie = ResponseMovies(emptyList(), 1, 1, 1)
        BDDMockito.given(movieService.fetchMoviesByQuery(1, "")).willReturn(Single.just(responseMovie))
        presenter.fetchMoviesByText(1, "")
        verify(view).resetPage(responseMovie)
    }

    @Test
    fun `should show error when fetch next page fails`() {
        BDDMockito.given(movieService.fetchMoviesByQuery(1, "")).willReturn(Single.error(Throwable()))
        presenter.fetchNextPage(1)
        verify(view).showError()
    }

    @Test
    fun `should update page when fetch movies by query is fetched`() {
        val responseMovie = ResponseMovies(emptyList(), 1, 1, 1)
        BDDMockito.given(movieService.fetchMoviesByQuery(1, "")).willReturn(Single.just(responseMovie))
        presenter.fetchNextPage(1)
        verify(view).updatePage(responseMovie)
    }


//
//    @Test
//    fun `should save movies in database when fetch movies is successful` () {
//        val responseMovie = ResponseMovies(emptyList(), 1,1 ,1)
//        BDDMockito.given(movieService.fetchMoviesByGenreId(1,1 )).willReturn(Single.just(responseMovie))
//        val list = ArrayList<Movie>()
//        BDDMockito.given(movieDao.insertMovies(list)).willReturn(Completable.complete())
//        presenter.fetchMovies(1,1)
//        verify(movieDao).insertMovies(list)
//    }

}