package com.irmiwiz.popcornflix.ui.viewmodel

import com.irmiwiz.popcornflix.domain.model.Movie

sealed class DetailActivityUiState {
    object ShowErrorMessage : DetailActivityUiState()
    class ShowMovieDetail(val movie: Movie) : DetailActivityUiState()
    object ShowLoading : DetailActivityUiState()
}
