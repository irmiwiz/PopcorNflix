package com.irmiwiz.popcornflix.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irmiwiz.popcornflix.core.helper.asLiveData
import com.irmiwiz.popcornflix.domain.model.MovieResult
import com.irmiwiz.popcornflix.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailActivityViewModel @Inject constructor(
    private val repo: MovieRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<DetailActivityUiState>()
    val viewState = _viewState.asLiveData()

    fun getMovieDetail(movieId: Int) {
        _viewState.value = DetailActivityUiState.ShowLoading
        viewModelScope.launch {
            _viewState.value = when (val response = repo.getMovieDetails(movieId)) {
                is MovieResult.Success -> DetailActivityUiState.ShowMovieDetail(response.data)
                else -> DetailActivityUiState.ShowErrorMessage
            }
        }
    }
}