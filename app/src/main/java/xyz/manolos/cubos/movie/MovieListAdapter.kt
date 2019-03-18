package xyz.manolos.cubos.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_list_item.view.*
import xyz.manolos.cubos.R
import xyz.manolos.cubos.model.Movie


private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/%s"

class MovieListAdapter(private val context: Context) : ListAdapter<Movie, MovieListAdapter.ViewHolder>(DiffCallback()) {


    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bindView(movie)
        holder.itemView.setOnClickListener {
        }

    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bindView(movie: Movie) {
            val image = itemView.posterImageView

            itemView.titleTextView.text = movie.title

            Picasso.get()
                .load(IMAGE_URL.format(movie.posterPath))
                .fit()
                .centerCrop()
                .into(image)
        }

    }


}

