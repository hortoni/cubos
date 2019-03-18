package xyz.manolos.cubos.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import xyz.manolos.cubos.R

class MovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentAdapter = MoviePagerAdapter(supportFragmentManager)
        movie_viewpager.adapter = fragmentAdapter

        movie_tabs.setupWithViewPager(movie_viewpager)
    }
}
