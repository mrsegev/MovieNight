package com.yossisegev.movienight

import android.os.Bundle
import android.support.design.widget.BottomNavigationView

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.yossisegev.movienight.favorites.FavoriteMoviesFragment
import com.yossisegev.movienight.popularmovies.PopularMoviesFragment
import com.yossisegev.movienight.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navigationBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PopularMoviesFragment(), "popular")
                    .commitNow()
            title = getString(R.string.popular)
        }

        navigationBar = bottomNavigationView
        navigationBar.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == navigationBar.selectedItemId) {
            return false
        }

        when (item.itemId) {

            R.id.action_popular -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PopularMoviesFragment(), "popular")
                        .commitNow()
                title = getString(R.string.popular)
            }

            R.id.action_favorites -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, FavoriteMoviesFragment(), "favorites")
                        .commitNow()
                title = getString(R.string.my_favorites)
            }

            R.id.action_search -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SearchFragment(), "search")
                        .commitNow()
                title = getString(R.string.search)
            }
        }

        return true
    }

}
