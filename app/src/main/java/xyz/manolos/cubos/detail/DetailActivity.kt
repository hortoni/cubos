package xyz.manolos.cubos.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import xyz.manolos.cubos.injector
import xyz.manolos.cubos.model.Movie
import xyz.manolos.inmovies.detail.DetailModule


private const val MOVIE = "movie"
private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/%s"

interface DetailView {
}

class DetailActivity : AppCompatActivity(), DetailView {

    companion object {
        fun newIntent(context: Context, movie: Movie): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(MOVIE, movie)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(xyz.manolos.cubos.R.layout.activity_detail)

        val movie = intent.getParcelableExtra(MOVIE) as Movie
        supportActionBar!!.title = movie.title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        injector
            .plusDetail(DetailModule(this))
            .inject(this)

        setupView(movie)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupView(movie: Movie) {
        movieOverviewTextview.text = movie.overview

        Picasso.get()
            .load(IMAGE_URL.format(movie.posterPath))
            .into(movieImageView)
    }

}
