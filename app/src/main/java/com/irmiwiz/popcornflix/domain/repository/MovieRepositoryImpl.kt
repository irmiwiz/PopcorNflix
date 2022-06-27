package com.irmiwiz.popcornflix.domain.repository

import com.irmiwiz.popcornflix.data.network.MovieClient
import com.irmiwiz.popcornflix.domain.model.Cast
import com.irmiwiz.popcornflix.domain.model.Movie
import com.irmiwiz.popcornflix.domain.model.MovieResult
import com.irmiwiz.popcornflix.domain.model.MoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject internal constructor(
    private val clientImpl: MovieClient
) {
    suspend fun getMovieDetails(movieId: Int): MovieResult<Movie> {
        return withContext(Dispatchers.IO) {
            clientImpl.getMovieDetails(movieId)
        }
    }

    suspend fun getListOfMovies(sortBy: String, page: Int): MovieResult<MoviesResponse> {
        return withContext(Dispatchers.IO) {
            clientImpl.getListOfMovies(sortBy, page)
        }
    }


    suspend fun searchMovies(query: String): MovieResult<MoviesResponse> {
        return withContext(Dispatchers.IO) {
            clientImpl.searchMovie(query)
        }
    }


    suspend fun getCast(movieId: Int): MovieResult<List<Cast>> {
        return withContext(Dispatchers.IO) {
            clientImpl.getCast(movieId)
        }
    }
}