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
class DetailFragmentViewModel @Inject constructor(
    private val repo: MovieRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<DetailFragmentUiState>()
    val uiState = _uiState.asLiveData()

    fun getCast(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = when (val response = repo.getCast(movieId)) {
                is MovieResult.Success -> DetailFragmentUiState.ShowCast(response.data)
                else -> DetailFragmentUiState.ShowErrorMessage
            }
        }
    }
}