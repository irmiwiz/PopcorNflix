package com.irmiwiz.popcornflix.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irmiwiz.popcornflix.core.helper.EMPTY_INT
import com.irmiwiz.popcornflix.core.helper.EMPTY_STRING
import com.irmiwiz.popcornflix.core.helper.QueryType
import com.irmiwiz.popcornflix.core.helper.asLiveData
import com.irmiwiz.popcornflix.domain.model.Movie
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


    private val _moviesList = MutableLiveData<List<Movie>>()
    val movies = _moviesList.asLiveData()

    private val _search = MutableLiveData<List<Movie>>()
    val search = _search.asLiveData()

    private val _error = MutableLiveData<Unit>()
    val error = _error.asLiveData()

    private var currentSort = QueryType.MOST_POPULAR.value
    private var currentPage = EMPTY_INT
    private var maxPage = EMPTY_INT

    fun getMovies(sortBy: String = currentSort) {
        currentSort = sortBy
        viewModelScope.launch {
            _uiState.value = when (val response = repo.getListOfMovies(sortBy, 1)) {
                is MovieResult.Success -> {
                    currentPage = response.data.page
                    maxPage = response.data.totalPages
                    _moviesList.postValue(response.data.movies)
                    MainActivityUiState.ShowMovies(response.data.movies)
                }
                else -> {
                    _error.postValue(Unit)
                    MainActivityUiState.Error
                }
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
                        _moviesList.postValue(response.data.movies)
                        MainActivityUiState.updateMovies(response.data.movies)
                    }
                    else -> {
                        _error.postValue(Unit)
                        MainActivityUiState.Error
                    }
                }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            _uiState.value =   when (val response = repo.searchMovies(query)) {
                is MovieResult.Success -> {
                    _search.postValue(response.data.movies)
                    MainActivityUiState.showSearchList(response.data.movies)}
                else -> {
                    _error.postValue(Unit)
                    MainActivityUiState.Error
                }
            }
        }
    }
}