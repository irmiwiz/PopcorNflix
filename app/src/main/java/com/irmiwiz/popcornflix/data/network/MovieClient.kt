package com.irmiwiz.popcornflix.data.network

import com.irmiwiz.popcornflix.BuildConfig
import com.irmiwiz.popcornflix.core.helper.executeRetrofitRequest
import com.irmiwiz.popcornflix.core.helper.handleResultRetrofit
import com.irmiwiz.popcornflix.domain.model.*
import javax.inject.Inject

class MovieClient @Inject internal constructor(
    private val movieApi: MovieApiService
){
    suspend fun getMovieDetails(movieId: Int): MovieResult<Movie> {
        val result = executeRetrofitRequest { movieApi.getMovieDetails(movieId, BuildConfig.API_KEY) }
        return handleResultRetrofit(result) { it.toMovie() }
    }

    suspend fun getListOfMovies(sortBy: String, page: Int): MovieResult<MoviesResponse> {
        val result = executeRetrofitRequest {
            movieApi.getListOfMovies(
                BuildConfig.API_KEY,
                sortBy,
                page = page
            )
        }
        return handleResultRetrofit(result) { it.toMoviesResponse() }
    }

    suspend fun searchMovie(query: String): MovieResult<MoviesResponse> {
        val result = executeRetrofitRequest { movieApi.searchMovie(BuildConfig.API_KEY, query) }
        return handleResultRetrofit(result) { it.toMoviesResponse() }
    }


    suspend fun getCast(movieId: Int): MovieResult<List<Cast>> {
        val result = executeRetrofitRequest { movieApi.getCast(movieId, BuildConfig.API_KEY) }
        return handleResultRetrofit(result) { it.toCastList() }
    }

}