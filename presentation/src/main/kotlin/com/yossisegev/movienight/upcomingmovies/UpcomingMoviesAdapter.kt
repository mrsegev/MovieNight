package com.yossisegev.movienight.upcomingmovies

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.yossisegev.movienight.common.ImageLoader
import com.yossisegev.movienight.entities.Movie
import kotlinx.android.synthetic.main.upcoming_movies_adapter_cell.view.*

class UpcomingMoviesAdapter constructor(private val imageLoader: ImageLoader,
                                        private val onMovieSelected: (Movie, View) -> Unit) : RecyclerView.Adapter<UpcomingMoviesAdapter.MovieCellViewHolder>() {

    private val movies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieCellViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: MovieCellViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class MovieCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie, imageLoader: ImageLoader, listener: (Movie, View) -> Unit) = with(itemView) {
            title_upcoming.text = movie.originalTitle
            movie.posterPath?.let { imageLoader.load(it, image_upcoming) }
            setOnClickListener { listener(movie, itemView) }
        }
    }
}