package xyz.manolos.cubos.movie

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.movie_fragment.*
import xyz.manolos.cubos.R
import xyz.manolos.cubos.model.ResponseMovies
import javax.inject.Inject


interface MovieView {
    fun update(it: ResponseMovies)
    fun showError()
    fun showLoading()
    fun hideLoading()
}

class MovieFragment: Fragment(), MovieView {

    @Inject
    private lateinit var presenter: MoviePresenter
    private lateinit var linearLayoutManager: GridLayoutManager
    private lateinit var adapter: MovieListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerview(this.context!!)

        presenter.fetchMovies(1, 18)

    }

    private fun setupRecyclerview(context: Context) {
        linearLayoutManager = GridLayoutManager(context,2)
        moviesList.layoutManager = linearLayoutManager
        adapter = MovieListAdapter(context)
        moviesList.adapter = adapter

    }

    override fun update(it: ResponseMovies) {
        adapter.submitList(it.results)
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