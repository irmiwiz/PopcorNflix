package com.irmiwiz.popcornflix.domain.model

import com.irmiwiz.popcornflix.core.helper.EMPTY_INT
import com.irmiwiz.popcornflix.data.model.MovieApi
import com.irmiwiz.popcornflix.data.model.MoviesResponseApi

data class MoviesResponse(
    var page: Int,
    var movies: List<Movie>,
    var totalResults: Int,
    var totalPages: Int
)

fun MoviesResponseApi.toMoviesResponse(): MoviesResponse {
    return MoviesResponse(
        page = page ?: EMPTY_INT,
        movies = movies.mapToMovies(),
        totalResults = totalResults ?: EMPTY_INT,
        totalPages = totalPages ?: EMPTY_INT
    )
}

fun List<MovieApi>?.mapToMovies(): List<Movie> {
    return this?.map {
        it.toMovie()
    } ?: listOf()
}