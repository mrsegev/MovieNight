package com.yossisegev.movienight.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.yossisegev.movienight.R
import com.yossisegev.movienight.common.App
import com.yossisegev.movienight.common.BaseFragment
import com.yossisegev.movienight.common.ImageLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_search_movies.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Yossi Segev on 11/11/2017.
 */
class SearchFragment : BaseFragment(), TextWatcher {
    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        searchSubject.onNext(s.toString())
    }

    @Inject
    lateinit var factory: SearchVMFactory
    @Inject
    lateinit var imageLoader: ImageLoader
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchEditText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var noResultsMessage: TextView
    private lateinit var searchResultsAdapter: SearchResultsAdapter
    private lateinit var searchSubject: PublishSubject<String>
    private val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as App).createSearchComponent().inject(this)
        viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)
        searchSubject = PublishSubject.create()

        //TODO: Handle screen rotation during debounce
        val disposable = searchSubject.debounce(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it != searchResultsAdapter.query) {
                        viewModel.search(it)
                    } else {
                        Log.i(javaClass.simpleName, "Same query -> aborting search")
                    }
                }

        compositeDisposable.add(disposable)
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

    private fun handleViewState(state: SearchViewState) {
        progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        val movies = state.movies ?: listOf()
        if (state.showNoResultsMessage) {
            noResultsMessage.visibility = View.VISIBLE
            noResultsMessage.text = String.format(
                    getString(R.string.search_no_results_message,
                            state.lastSearchedQuery))
        } else {
            noResultsMessage.visibility = View.GONE
        }
        searchResultsAdapter.setResults(movies, state.lastSearchedQuery)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_search_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchEditText = search_movies_edit_text
        searchEditText.addTextChangedListener(this)
        progressBar = search_movies_progress
        noResultsMessage = search_movies_no_results_message
        searchResultsAdapter = SearchResultsAdapter(imageLoader, { movie, movieView ->
            showSoftKeyboard(false)
            navigateToMovieDetailsScreen(movie, movieView)
        })
        recyclerView = search_movies_recyclerview
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = searchResultsAdapter
        searchEditText.requestFocus()
        showSoftKeyboard(true)
    }

    private fun showSoftKeyboard(show: Boolean) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (show) {
           imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
        } else {
            imm.hideSoftInputFromWindow(searchEditText.windowToken,0)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("lastSearch", searchEditText.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showSoftKeyboard(false)
        compositeDisposable.clear()
        (activity?.application as App).releaseSearchComponent()
    }

}