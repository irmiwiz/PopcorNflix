package com.irmiwiz.popcornflix.ui.view

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irmiwiz.popcornflix.R
import com.irmiwiz.popcornflix.core.helper.*
import com.irmiwiz.popcornflix.databinding.ActivityMainBinding
import com.irmiwiz.popcornflix.domain.model.Movie
import com.irmiwiz.popcornflix.ui.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , MoviesAdapter.OnItemClickListener {

    private val viewModel: MainActivityViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    private var queryType = QueryType.MOST_POPULAR.value
    private var isBusy = false
    private var page = 1
    private val adapter = MoviesAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        setUpToolbar()
        setUpBottomToolbar()
        getMovies()
        addObservers()
        showProgressBar()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerMovies.layoutManager =
                GridLayoutManager(this, 6, RecyclerView.VERTICAL, false)
        } else {
            binding.recyclerMovies.layoutManager =
                GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        }
        binding.recyclerMovies.adapter = adapter
    }

    private fun addObservers() {
        viewModel.apply {
            error.observe(this@MainActivity,  { handleErrorResponse() })
            movies.observe(this@MainActivity, ::refreshScreen)
            search.observe(this@MainActivity, ::refreshSearch)
        }
    }

    private fun setUpBottomToolbar() {
        binding.bottomToolbar.setOnNavigationItemSelectedListener { item ->
            navigateMenu(item)
            true
        }
    }

    private fun setUpToolbar() {
        binding.toolbar.apply {
            btnBack.setOnClickListener { closeSearchView() }
            btnSearch.setOnClickListener { showSearchView() }
            btnClear.setOnClickListener { clearSearchText() }
            txtSearch.setOnKeyListener { _, keyCode, _ ->
                if (keyCode ==  KeyEvent.KEYCODE_ENTER){
                    binding.toolbar.btnClear.visibility = View.VISIBLE
                    viewModel.searchMovies(txtSearch.text.toString())
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
            txtSearch.doAfterTextChanged {
                it?.let { editable ->
                    if (editable.isEmpty()) {
                        searchCleared()
                    }
                }
            }
        }
    }

    private fun searchCleared() {
        binding.toolbar.btnClear.visibility = View.GONE
        resetValues()
        viewModel.getMovies(queryType, page)
    }

    private fun closeSearchView() {
        binding.toolbar.apply {
            titleGroup.visibility = View.VISIBLE
            searchGroup.visibility = View.GONE
            btnClear.visibility = View.GONE
            clearSearchText()
        }
        binding.toolbar.txtSearch.hideSoftKeyBoard(this)
        binding.bottomToolbar.visibility = View.VISIBLE
    }

    private fun showSearchView() {
        binding.toolbar.apply {
            titleGroup.visibility = View.GONE
            searchGroup.visibility = View.VISIBLE
        }
        binding.bottomToolbar.visibility = View.GONE
    }

    private fun clearSearchText() {
        binding.toolbar.txtSearch.setText(EMPTY_STRING)
    }

    private fun navigateMenu(item: MenuItem) {
        if (!isBusy) {
            showProgressBar()
            when (item.itemId) {
                R.id.most_pupular -> {
                    queryType = QueryType.MOST_POPULAR.value
                    resetValues()
                }
                R.id.most_recent -> {
                    queryType = QueryType.MOST_RECENT.value
                    resetValues()
                }
                else -> {
                    queryType = QueryType.BEST_RATED.value
                    resetValues()
                }
            }
        }
    }

    private fun resetValues() {
        adapter.clear()
        page = 1
        getMovies()
    }

    private fun getMovies() {
        viewModel.getMovies(queryType, page)
        page.plus(1)
    }

    private fun handleErrorResponse() {
        hideProgressBar()
        binding.apply {
            txtError.visibility = View.VISIBLE
            recyclerMovies.visibility = View.GONE
        }
    }

    private fun hideErrorMsg() {
        hideProgressBar()
        binding.apply {
            txtError.visibility = View.GONE
            recyclerMovies.visibility = View.VISIBLE
        }
    }

    private fun refreshSearch(movies: List<Movie>) {
        resetValues()
        refreshScreen(movies)
    }

    private fun refreshScreen(movies: List<Movie>) {
        hideErrorMsg()
        adapter.onMoviesUpdated(movies)
    }

    override fun onBackPressed() {
        if (binding.toolbar.searchGroup.isVisible) {
            closeSearchView()
        } else {
            super.onBackPressed()
        }
    }

    private fun showProgressBar() {
        isBusy = true
        binding.progressBar.toggleVisibility(true)
    }

    private fun hideProgressBar() {
        isBusy = false
        binding.progressBar.toggleVisibility(false)
    }

    override fun onItemClick(movie: Movie) {
        goToDetailActivity(movie.id)
    }

    override fun getMoreMovies() {
        getMovies()
    }

    private fun goToDetailActivity(movieId: Int) {
        launchActivity<DetailActivity> {
            putExtra(KEY_MOVIE_ID, movieId)
        }
    }
}