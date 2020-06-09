package com.yossisegev.movienight.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.Slide
import android.transition.TransitionManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import co.lujun.androidtagview.TagContainerLayout
import com.yossisegev.movienight.R
import com.yossisegev.movienight.common.App
import com.yossisegev.movienight.common.ImageLoader
import com.yossisegev.movienight.common.SimpleTransitionEndedCallback
import com.yossisegev.movienight.entities.Video
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.details_overview_section.*
import kotlinx.android.synthetic.main.details_video_section.*
import javax.inject.Inject

class MovieDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: MovieDetailsVMFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var detailsViewModel: MovieDetailsViewModel
    private lateinit var backdropImage: ImageView
    private lateinit var posterImage: ImageView
    private lateinit var title: TextView
    private lateinit var overview: TextView
    private lateinit var releaseDate: TextView
    private lateinit var score: TextView
    private lateinit var videos: RecyclerView
    private lateinit var videosSection: View
    private lateinit var backButton: View
    private lateinit var tagsContainer: TagContainerLayout
    private lateinit var favoriteButton: FloatingActionButton

    companion object {
        private const val MOVIE_ID: String = "extra_movie_id"
        private const val MOVIE_POSTER_URL: String = "extra_movie_poster_url"

        fun newIntent(context: Context, movieId: Int, posterUrl: String?): Intent {
            val i = Intent(context, MovieDetailsActivity::class.java)
            i.putExtra(MOVIE_ID, movieId)
            i.putExtra(MOVIE_POSTER_URL, posterUrl)
            return i
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        postponeEnterTransition()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE and View.SYSTEM_UI_FLAG_FULLSCREEN

        (application as App).createDetailsComponent().inject(this)

        factory.movieId = intent.getIntExtra(MOVIE_ID, 0)
        detailsViewModel = ViewModelProviders.of(this, factory).get(MovieDetailsViewModel::class.java)
        backButton = details_back_button
        backButton.setOnClickListener { finish() }
        favoriteButton = details_favorite_fab
        favoriteButton.setOnClickListener { detailsViewModel.favoriteButtonClicked() }
        backdropImage = details_backdrop
        posterImage = details_poster
        title = details_title
        overview = details_overview
        releaseDate = details_release_date
        tagsContainer = details_tags
        score = details_score
        videos = details_videos
        videosSection = details_video_section

        val posterUrl = intent.getStringExtra(MOVIE_POSTER_URL)
        posterUrl?.let {
            imageLoader.load(it, posterImage) {
                startPostponedEnterTransition()
            }
        } ?: run {
            startPostponedEnterTransition()
        }

        window.sharedElementEnterTransition.addListener(SimpleTransitionEndedCallback {
            observeViewState()
        })

        // If we don't have any entering transition
        if (savedInstanceState != null) {
            observeViewState()
        } else {
            detailsViewModel.getMovieDetails()
        }
    }

    private fun observeViewState() {
        detailsViewModel.viewState.observe(this, Observer { viewState -> handleViewState(viewState) })
        detailsViewModel.favoriteState.observe(this, Observer { favorite -> handleFavoriteStateChange(favorite) })
        detailsViewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun onVideoSelected(video: Video) {
        video.url?.let {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(browserIntent)
        }
    }

    private fun handleViewState(state: MovieDetailsViewState?) {
        if (state == null) return

        title.text = state.title
        overview.text = state.overview
        releaseDate.text = String.format(getString(R.string.release_date_template, state.releaseDate))
        score.text = if (state.votesAverage == 0.0) getString(R.string.n_a) else state.votesAverage.toString()
        state.genres?.let { tagsContainer.tags = state.genres }

        val transition = Slide()
        transition.excludeTarget(posterImage, true)
        transition.duration = 750
        TransitionManager.beginDelayedTransition(details_root_view, transition)
        title.visibility = View.VISIBLE
        releaseDate.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
        details_release_date_layout.visibility = View.VISIBLE
        details_score_layout.visibility = View.VISIBLE
        details_overview_section.visibility = View.VISIBLE
        videosSection.visibility = View.VISIBLE
        tagsContainer.visibility = View.VISIBLE

        state.backdropUrl?.let { imageLoader.load(it, backdropImage) }

        state.videos?.let {
            val videosAdapter = VideosAdapter(it, this::onVideoSelected)
            videos.layoutManager = LinearLayoutManager(this)
            videos.adapter = videosAdapter

        } ?: run {
            videosSection.visibility = View.GONE
        }
    }

    private fun handleFavoriteStateChange(favorite: Boolean?) {
        if (favorite == null) return
        favoriteButton.visibility = View.VISIBLE
        favoriteButton.setImageDrawable(
            if (favorite)
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_36dp)
            else
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_36dp))
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as App).releaseDetailsComponent()
    }
}
