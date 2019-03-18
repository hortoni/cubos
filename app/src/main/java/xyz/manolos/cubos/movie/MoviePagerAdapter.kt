package xyz.manolos.cubos.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

private const val ID_ACTION: Long = 28
private const val ID_DRAMA: Long = 18
private const val ID_FANTASY: Long = 14
private const val ID_FICTION: Long = 878

class MoviePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val fragment = MovieFragment()
        val bundle = Bundle()
        when (position) {
            0 -> {
                bundle.putLong("id", ID_ACTION)
            }
            1 -> {
                bundle.putLong("id", ID_DRAMA)
            }
            2 -> {
                bundle.putLong("id", ID_FANTASY)
            }
            else -> {
                bundle.putLong("id", ID_FICTION)
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
            0 -> "Ação"
            1 -> "Drama"
            2 -> "Fantasia"
            else -> {
                return "Ficção"
            }
        }
    }

}