package com.yossisegev.movienight.favorites


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yossisegev.movienight.R
import com.yossisegev.movienight.common.ImageLoader
import com.yossisegev.movienight.entities.Movie
import kotlinx.android.synthetic.main.favorite_movies_adapter_row.view.*


/**
 * Created by Yossi Segev on 14/11/2017.
 */
class FavoriteMoviesAdapter constructor(private val imageLoader: ImageLoader,
                                        private val onMovieSelected: (Movie, View) -> Unit) : RecyclerView.Adapter<FavoriteMoviesAdapter.MovieCellViewHolder>() {

    private var movies: List<Movie> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieCellViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(
                R.layout.favorite_movies_adapter_row,
                parent,
                false)
        return MovieCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieCellViewHolder?, position: Int) {
        val movie = movies[position]
        holder?.bind(movie, imageLoader, onMovieSelected)
    }

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    class MovieCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie, imageLoader: ImageLoader, listener: (Movie, View) -> Unit) = with(itemView) {
            title.text = movie.originalTitle
            movie.posterPath?.let { imageLoader.load(it, image) }

            movie.overview?.let {
                overview.text = movie.overview
                overview.visibility = View.VISIBLE
            } ?: run {
                overview.visibility = View.GONE
            }
            setOnClickListener { listener(movie, itemView) }
        }

    }
}