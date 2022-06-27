package com.irmiwiz.popcornflix.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irmiwiz.popcornflix.core.helper.EMPTY_INT
import com.irmiwiz.popcornflix.core.helper.QueryType
import com.irmiwiz.popcornflix.core.helper.asLiveData
import com.irmiwiz.popcornflix.domain.model.MovieResult
import com.irmiwiz.popcornflix.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repo: MovieRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<MainActivityUiState>()
    val uiState = _uiState.asLiveData()

    private var currentSort = QueryType.MOST_POPULAR.value
    private var currentPage = EMPTY_INT
    private var maxPage = EMPTY_INT

    fun getMovies(sortBy: String = currentSort) {
        _uiState.value = MainActivityUiState.Loading
        currentSort = sortBy
        viewModelScope.launch {
            _uiState.value = when (val response = repo.getListOfMovies(sortBy, 1)) {
                is MovieResult.Success -> {
                    currentPage = response.data.page
                    maxPage = response.data.totalPages
                    MainActivityUiState.ShowMovies(response.data.movies)
                }
                else -> MainActivityUiState.Error

            }
        }
    }

    fun getMoreMovies() {
        viewModelScope.launch {
            _uiState.value =
                when (val response = repo.getListOfMovies(currentSort, currentPage.inc())) {
                    is MovieResult.Success -> {
                        currentPage = response.data.page
                        maxPage = response.data.totalPages
                        MainActivityUiState.UpdateMovies(response.data.movies)
                    }
                    else -> MainActivityUiState.Error

                }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            _uiState.value = when (val response = repo.searchMovies(query)) {
                is MovieResult.Success -> {
                    MainActivityUiState.ShowSearchList(response.data.movies)
                }
                else -> MainActivityUiState.Error

            }
        }
    }
}