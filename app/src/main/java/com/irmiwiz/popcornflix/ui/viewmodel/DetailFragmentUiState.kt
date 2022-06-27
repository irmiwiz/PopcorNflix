package com.irmiwiz.popcornflix.ui.viewmodel

import com.irmiwiz.popcornflix.domain.model.Cast

sealed class DetailFragmentUiState{
    object ShowErrorMessage : DetailFragmentUiState()
    class ShowCast(val cast: List<Cast>) : DetailFragmentUiState()
}

