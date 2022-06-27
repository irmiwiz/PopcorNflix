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
import com.irmiwiz.popcornflix.ui.viewmodel.MainActivityUiState
import com.irmiwiz.popcornflix.ui.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    lateinit var binding: ActivityMainBinding

    private val onMovieClicked: (movie: Movie) -> Unit = {
        goToDetailActivity(it.id)
    }

    private val getMoreMovies: (movide: Movie) -> Unit = {
        viewModel.getMoreMovies()
    }

    private val adapter = MoviesAdapter(onMovieClicked = onMovieClicked, getMoreData = getMoreMovies)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        setUpToolbar()
        setUpBottomToolbar()
        viewModel.uiState.observe(this@MainActivity, ::observeUiState)
        viewModel.getMovies()
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

    private fun observeUiState(uiState: MainActivityUiState) {
        binding.progressBar.toggleVisibility(uiState is MainActivityUiState.Loading)
        showErrorView(uiState is MainActivityUiState.Error)
        when (uiState) {
            is MainActivityUiState.ShowMovies -> adapter.submitList(uiState.movieList)
            is MainActivityUiState.UpdateMovies -> adapter.updateList(uiState.movieList)
            is MainActivityUiState.ShowSearchList -> adapter.submitList(uiState.movieList)
        }
    }

    private fun showErrorView(show: Boolean) {
        binding.apply {
            txtError.toggleVisibility(show)
            recyclerMovies.toggleVisibility(show.not())
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
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
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
        viewModel.getMovies()
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
        when (item.itemId) {
            R.id.most_pupular -> {
                viewModel.getMovies(QueryType.MOST_POPULAR.value)
            }
            R.id.most_recent -> {
                viewModel.getMovies(QueryType.MOST_RECENT.value)
            }
            else -> {
                viewModel.getMovies(QueryType.BEST_RATED.value)
            }
        }
    }

    override fun onBackPressed() {
        if (binding.toolbar.searchGroup.isVisible) {
            closeSearchView()
        } else {
            super.onBackPressed()
        }
    }

    private fun goToDetailActivity(movieId: Int) {
        launchActivity<DetailActivity> {
            putExtra(KEY_MOVIE_ID, movieId)
        }
    }
}