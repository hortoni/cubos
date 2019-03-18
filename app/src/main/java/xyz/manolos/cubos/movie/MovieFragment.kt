package xyz.manolos.cubos.movie

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.movie_fragment.*
import xyz.manolos.cubos.R
import xyz.manolos.cubos.injector
import xyz.manolos.cubos.model.ResponseMovies
import javax.inject.Inject


interface MovieView {
    fun updatePage(it: ResponseMovies)
    fun showError()
    fun showLoading()
    fun hideLoading()
}

class MovieFragment : Fragment(), MovieView {

    @Inject
    lateinit var presenter: MoviePresenter
    private lateinit var linearLayoutManager: GridLayoutManager
    private lateinit var adapter: MovieListAdapter
    private var page: Int = 1
    private var genreId: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity!!.injector
            .plusMovie(MovieModule(this))
            .inject(this)

        return inflater.inflate(R.layout.movie_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerview(this.context!!)
        genreId = this.arguments!!.getLong("id")
        presenter.fetchMovies(page, genreId)

        presenter.fetchMovies(page, genreId)

        presenter.observeMoviesByGenreId(genreId).observe(this, Observer {
            adapter.submitList(it)
        })

        swipeLayout.setOnRefreshListener {
            presenter.fetchMovies(page, genreId)
        }
    }

    private fun setupRecyclerview(context: Context) {
        linearLayoutManager = GridLayoutManager(context, 2)
        moviesList.layoutManager = linearLayoutManager
        adapter = MovieListAdapter(context)
        moviesList.adapter = adapter
        moviesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val total = linearLayoutManager.itemCount
                val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                val isNearEnd = total - 1 == lastVisibleItem
                if (isNearEnd && !swipeLayout.isRefreshing && page != -1) {
                    presenter.fetchMovies(page, genreId)
                }
            }
        })

    }

    override fun updatePage(it: ResponseMovies) {
        adapter.submitList(it.results)
        if (it.totalPages == page) {
            page = -1
        } else {
            page++
        }
    }

    override fun showError() {
        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        swipeLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeLayout.isRefreshing = false
    }

}