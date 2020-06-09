package com.yossisegev.movienight.popularmovies

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.yossisegev.movienight.R
import com.yossisegev.movienight.common.App
import com.yossisegev.movienight.common.BaseFragment
import com.yossisegev.movienight.common.ImageLoader
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import javax.inject.Inject

/**
 * Created by Yossi Segev on 11/11/2017.
 */
class PopularMoviesFragment : BaseFragment() {

    @Inject
    lateinit var factory: PopularMoviesVMFactory

    @Inject
    lateinit var imageLoader: ImageLoader
    private lateinit var viewModel: PopularMoviesViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).createPopularComponent().inject(this)
        viewModel = ViewModelProviders.of(this, factory).get(PopularMoviesViewModel::class.java)

        if (savedInstanceState == null) {
            viewModel.getPopularMovies()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        viewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(activity, throwable.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun handleViewState(state: PopularMoviesViewState) {
        progressBar.visibility = if (state.showLoading) View.VISIBLE else View.GONE
        state.movies?.let { popularMoviesAdapter.addMovies(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_popular_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = popular_movies_progress
        popularMoviesAdapter = PopularMoviesAdapter(imageLoader) { movie, view ->
            navigateToMovieDetailsScreen(movie, view)
        }
        recyclerView = popular_movies_recyclerview
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.adapter = popularMoviesAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity?.application as App).releasePopularComponent()
    }
}