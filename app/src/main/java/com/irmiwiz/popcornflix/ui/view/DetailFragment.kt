package com.irmiwiz.popcornflix.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.irmiwiz.popcornflix.BuildConfig
import com.irmiwiz.popcornflix.R
import com.irmiwiz.popcornflix.core.helper.KEY_MOVIE_ID
import com.irmiwiz.popcornflix.core.helper.toggleVisibility
import com.irmiwiz.popcornflix.databinding.FragmentDetailBinding
import com.irmiwiz.popcornflix.domain.model.Movie
import com.irmiwiz.popcornflix.ui.viewmodel.DetailFragmentUiState
import com.irmiwiz.popcornflix.ui.viewmodel.DetailFragmentViewModel
import com.irmiwiz.popcornflix.ui.viewmodel.MovieDetailSharedViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel : DetailFragmentViewModel by viewModels()
    private val sharedViewModel : MovieDetailSharedViewModel by activityViewModels()

    lateinit var binding: FragmentDetailBinding
    private var movie: Movie? = null
    private val castAdapter = CastAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = sharedViewModel.getDetail()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateScreen()
        addObservers()
        setUpAdapter()
    }

    private fun addObservers() {
        viewModel.apply {
            uiState.observe(viewLifecycleOwner, ::handleUiState)
        }
    }


    private fun handleUiState(uiState: DetailFragmentUiState) {
        when (uiState) {
            is DetailFragmentUiState.ShowCast -> castAdapter.submitList(uiState.cast)
            else -> binding.cast.toggleVisibility(false)
        }
    }

    private fun setUpAdapter() {
        binding.cast.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.cast.adapter = castAdapter
    }

    private fun populateScreen() {
        movie?.id?.let {
            viewModel.getCast(it)
        }
        binding.apply {
            movie?.let {
                overview.text = it.overview
                rating.text = it.raiting.toString()
                year.text = it.releaseDate.substring(0, 4)
                it.tagline.let { text ->
                    tagline.text = text
                }
                playButton.toggleVisibility(it.video)
                playButton.setOnClickListener { Toast.makeText(context, "Comming soon", Toast.LENGTH_SHORT).show() }
            }
        }

        Glide.with(this)
            .load(BuildConfig.POSTER_BASE_URL + movie?.posterPath)
            .centerCrop()
            .error(R.drawable.ic_error)
            .into(binding.poster)
    }

    companion object {
        fun newInstance(id: Int) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_MOVIE_ID, id)
                }
            }
    }
}