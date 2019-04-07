package com.yossisegev.movienight.upcomingmovies

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
import kotlinx.android.synthetic.main.fragment_upcoming_movies.*
import javax.inject.Inject

class UpcomingMoviesFragment : BaseFragment() {

    @Inject
    lateinit var factory: UpcomingMoviesVMFactory
    @Inject
    lateinit var imageLoader: ImageLoader
    private lateinit var viewModel: UpcomingMoviesViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UpcomingMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).createUpcomingComponent().inject(this)
        viewModel = ViewModelProviders.of(this, factory).get(UpcomingMoviesViewModel::class.java)

        if (savedInstanceState == null) {
            viewModel.getUpcomingMovies()
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

    private fun handleViewState(state: UpcomingMoviesViewState) {
        progressBar.visibility = if (state.showLoading) View.VISIBLE else View.GONE
        state.movies?.let { adapter.addMovies(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_upcoming_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = upcoming_movies_progress
        adapter = UpcomingMoviesAdapter(imageLoader) { movie, view ->
            navigateToMovieDetailsScreen(movie, view)
        }
        recyclerView = upcoming_movies_recyclerview
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity?.application as App).releaseUpcomingComponent()
    }
}