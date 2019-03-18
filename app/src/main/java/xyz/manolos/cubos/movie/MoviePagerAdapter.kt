package xyz.manolos.cubos.movie

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MoviePagerAdapter (fm: FragmentManager) :FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return MovieFragment()
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Ação"
            1 -> "Drama"
            2 -> "Fantasia"
            else -> {
                return "Ficção"
            }
        }
    }

}