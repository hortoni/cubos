package xyz.manolos.cubos.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.movie_fragment.*
import xyz.manolos.cubos.R
import xyz.manolos.cubos.injector
import xyz.manolos.cubos.model.ResponseMovies
import xyz.manolos.cubos.movie.MovieListAdapter
import javax.inject.Inject


interface SearchResultsView {
    fun updatePage(it: ResponseMovies)
    fun showError()
    fun showLoading()
    fun hideLoading()
}

class SearchResultsActivity : AppCompatActivity(), SearchResultsView{

    @Inject
    lateinit var presenter: SearchResultsPresenter
    private lateinit var linearLayoutManager: GridLayoutManager
    private lateinit var adapter: MovieListAdapter
    var query = ""
    private var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        injector
            .plusSearchResults(SearchResultsModule(this))
            .inject(this)

        handleIntent(intent)
        setupRecyclerview(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            query = intent.getStringExtra(SearchManager.QUERY)
            presenter.fetchMoviesByText(page, query)
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
                    presenter.fetchMoviesByText(page, query)
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
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        swipeLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeLayout.isRefreshing = false
    }

}

//https://api.themoviedb.org/3/search/movies?page=1&query=mad&api_key=d71ff64de15d4ed68bd780ce30e5b24c&language=pt-BR
//https://api.themoviedb.org/3/search/movie?api_key=d71ff64de15d4ed68bd780ce30e5b24c&language=en-US&query=mad&page=1