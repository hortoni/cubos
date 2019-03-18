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
import kotlinx.android.synthetic.main.activity_search_results.*
import xyz.manolos.cubos.R
import xyz.manolos.cubos.injector
import xyz.manolos.cubos.model.Movie
import xyz.manolos.cubos.model.ResponseMovies
import xyz.manolos.cubos.movie.MovieListAdapter
import javax.inject.Inject


interface SearchResultsView {
    fun updatePage(it: ResponseMovies)
    fun resetPage(it: ResponseMovies)
    fun showError()
    fun showLoading()
    fun hideLoading()
}

class SearchResultsActivity : AppCompatActivity(), SearchResultsView{

    @Inject
    lateinit var presenter: SearchResultsPresenter
    private lateinit var linearLayoutManager: GridLayoutManager
    private lateinit var adapter: MovieListAdapter
    var queryText = ""
    private var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        injector
            .plusSearchResults(SearchResultsModule(this))
            .inject(this)

        handleIntent(intent)
        setupRecyclerview(this)

        swipeSearchResultsLayout.setOnRefreshListener {
            presenter.fetchMoviesByText(page, queryText)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchItem = menu.findItem(R.id.search)

        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView

            searchView.setQuery(queryText, true)
            searchView.isIconified = false
            searchView.clearFocus()

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    presenter.fetchMoviesByText(1, query!!)
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }



        return true
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0,0)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            queryText = intent.getStringExtra(SearchManager.QUERY)
            presenter.fetchMoviesByText(page, queryText)
        }
    }

    private fun setupRecyclerview(context: Context) {
        linearLayoutManager = GridLayoutManager(context, 2)
        moviesSearchResultsList.layoutManager = linearLayoutManager
        adapter = MovieListAdapter(context)
        moviesSearchResultsList.adapter = adapter
        moviesSearchResultsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val total = linearLayoutManager.itemCount
                val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                val isNearEnd = total - 1 == lastVisibleItem
                if (isNearEnd && !swipeSearchResultsLayout.isRefreshing && page != -1) {
                    presenter.fetchNextPage(page)
                }
            }
        })
    }

    override fun updatePage(it: ResponseMovies) {
        if (adapter.currentList.isEmpty()) {
            adapter.submitList(it.results)
        } else {
            adapter.submitList(getListFromLists(adapter.currentList, it.results))
        }

        if (it.totalPages == page) {
            page = -1
        } else {
            page++
        }
    }

    override fun resetPage(it: ResponseMovies) {
        page = it.page + 1
        adapter.submitList(it.results)

    }

    override fun showError() {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        swipeSearchResultsLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeSearchResultsLayout.isRefreshing = false
    }

    fun getListFromLists(list1: List<Movie>, list2: List<Movie>) : List<Movie> {
        val list = ArrayList<Movie>()
        list.addAll(list1)
        list.addAll(list2)
        return list
    }

}