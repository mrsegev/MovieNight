package com.yossisegev.movienight.search


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yossisegev.movienight.R
import com.yossisegev.movienight.common.ImageLoader
import com.yossisegev.movienight.entities.Movie
import kotlinx.android.synthetic.main.search_results_adapter_row.view.*

/**
 * Created by Yossi Segev on 14/11/2017.
 */
class SearchResultsAdapter constructor(private val imageLoader: ImageLoader,
                                       private val onMovieSelected: (Movie, View) -> Unit) : RecyclerView.Adapter<SearchResultsAdapter.MovieCellViewHolder>() {

    private var movies: List<Movie> = listOf()
    var query: String? = null

    fun setResults(movies: List<Movie>, query: String?) {
        this.movies = movies
        this.query = query
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieCellViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(
                R.layout.search_results_adapter_row,
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

    class MovieCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie, imageLoader: ImageLoader, listener: (Movie, View) -> Unit) = with(itemView) {
            title.text = movie.originalTitle
            rating.text = movie.voteAverage.toString()

            movie.overview?.let {
                overview.text = movie.overview
                overview.visibility = View.VISIBLE
            } ?: run {
                overview.visibility = View.GONE
            }

            movie.posterPath?.let {
                image.visibility = View.VISIBLE
                imageLoader.load(it, image)
            } ?: run { image.visibility == View.INVISIBLE }

            setOnClickListener { listener(movie, itemView) }
        }

    }
}