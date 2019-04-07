package com.yossisegev.movienight.upcomingmovies

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yossisegev.movienight.R
import com.yossisegev.movienight.common.ImageLoader
import com.yossisegev.movienight.entities.Movie
import kotlinx.android.synthetic.main.upcoming_movies_adapter_cell.view.*

class UpcomingMoviesAdapter constructor(private val imageLoader: ImageLoader,
                                        private val onMovieSelected: (Movie, View) -> Unit) : RecyclerView.Adapter<UpcomingMoviesAdapter.MovieCellViewHolder>() {

    private val movies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieCellViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(
                R.layout.upcoming_movies_adapter_cell,
                parent,
                false)

        return UpcomingMoviesAdapter.MovieCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: UpcomingMoviesAdapter.MovieCellViewHolder?, position: Int) {
        val movie = movies[position]
        holder?.bind(movie, imageLoader, onMovieSelected)
    }

    fun addMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    class MovieCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie, imageLoader: ImageLoader, listener: (Movie, View) -> Unit) = with(itemView) {
            title_upcoming.text = movie.originalTitle
            movie.posterPath?.let { imageLoader.load(it, image_upcoming) }
            setOnClickListener { listener(movie, itemView) }
        }
    }
}