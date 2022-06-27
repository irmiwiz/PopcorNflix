package com.irmiwiz.popcornflix.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _moviesList = MutableLiveData<List<Movie>>()
    val movies = _moviesList.asLiveData()

    private val _search = MutableLiveData<List<Movie>>()
    val search = _search.asLiveData()

    private val _error = MutableLiveData<Unit>()
    val error = _error.asLiveData()

    fun getMovies(sortBy: String, page: Int) {
        viewModelScope.launch {
            when (val response = repo.getListOfMovies(sortBy, page)) {
                is MovieResult.Success -> _moviesList.postValue(response.data.movies)
                else -> _error.postValue(Unit)
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            when (val response = repo.searchMovies(query)) {
                is MovieResult.Success -> _search.postValue(response.data.movies)
                else -> _error.postValue(Unit)
            }
        }
    }
}