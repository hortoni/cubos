package xyz.manolos.cubos.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MoviePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val fragment = MovieFragment()
        val bundle = Bundle()
        when (position) {
            0 -> {
                bundle.putInt("id", 28)
            }
            1 -> {
                bundle.putInt("id", 18)
            }
            2 -> {
                bundle.putInt("id", 14)
            }
            else -> {
                bundle.putInt("id", 878)
            }
        }
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 ->  "Ação"
            1 -> "Drama"
            2 -> "Fantasia"
            else -> {
                return "Ficção"
            }
        }
    }

}