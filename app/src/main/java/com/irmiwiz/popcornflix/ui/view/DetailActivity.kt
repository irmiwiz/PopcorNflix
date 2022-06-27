package com.irmiwiz.popcornflix.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.irmiwiz.popcornflix.BuildConfig
import com.irmiwiz.popcornflix.R
import com.irmiwiz.popcornflix.core.helper.KEY_MOVIE_ID
import com.irmiwiz.popcornflix.core.helper.toggleVisibility
import com.irmiwiz.popcornflix.databinding.ActivityDetailBinding
import com.irmiwiz.popcornflix.domain.model.Movie
import com.irmiwiz.popcornflix.ui.viewmodel.DetailActivityUiState
import com.irmiwiz.popcornflix.ui.viewmodel.MovieDetailSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel : MovieDetailSharedViewModel by viewModels()
    var movieId: Int = 0
    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        addObservers()
        getId()
        setUpToolbar()
    }

    private fun getId() {
        movieId = intent.getIntExtra(KEY_MOVIE_ID, 0)
        if ((movieId == 0).not()) {
            getMovieDetail()
        } else {
            binding.txtError.toggleVisibility(true)
        }
    }

    private fun addObservers() {
        viewModel.apply {
            viewState.observe(this@DetailActivity, ::handleViewStateResponse)
        }
    }

    private fun handleViewStateResponse(uiState: DetailActivityUiState) {
        binding.progressBar.toggleVisibility(uiState is DetailActivityUiState.ShowLoading)
        when (uiState) {
            is DetailActivityUiState.ShowMovieDetail -> addFragment(uiState.movie)
            is DetailActivityUiState.ShowErrorMessage -> binding.txtError.toggleVisibility(true)
        }
    }

    private fun setUpToolbar() {
        binding.apply {
            setSupportActionBar(toolbarDetail)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun addFragment(movie: Movie) {
        binding.apply {
            collapsingToolbar.title = movie.originalTitle
            Glide.with(applicationContext)
                .load(BuildConfig.BACKDROP_BASE_URL + movie.backDropPath)
                .centerCrop()
                .error(R.drawable.ic_error)
                .into(binding.backdropImage)
        }
        val fragment = DetailFragment.newInstance(movie.id)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun getMovieDetail() {
        viewModel.getMovieDetail(movieId)
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
        super.onBackPressed()
    }
}