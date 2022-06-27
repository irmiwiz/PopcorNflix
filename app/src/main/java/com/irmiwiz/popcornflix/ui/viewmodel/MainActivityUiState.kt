package com.irmiwiz.popcornflix.ui.viewmodel

import com.irmiwiz.popcornflix.domain.model.Movie

sealed class MainActivityUiState{
    object Error : MainActivityUiState()
    object Loading : MainActivityUiState()
    data class ShowMovies(val movieList: List<Movie>): MainActivityUiState()
    data class UpdateMovies(val movieList: List<Movie>): MainActivityUiState()
    data class ShowSearchList(val movieList: List<Movie>): MainActivityUiState()

}
