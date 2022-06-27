package com.irmiwiz.popcornflix

import com.irmiwiz.popcornflix.core.helper.EMPTY_INT
import com.irmiwiz.popcornflix.core.helper.EMPTY_STRING
import com.irmiwiz.popcornflix.domain.model.Cast
import com.irmiwiz.popcornflix.domain.model.Movie
import com.irmiwiz.popcornflix.domain.model.MoviesResponse

fun getMovie() = Movie(
    backDropPath = EMPTY_STRING,
    id = EMPTY_INT,
    originalTitle = EMPTY_STRING,
    title = EMPTY_STRING,
    posterPath = EMPTY_STRING,
    logoPath = EMPTY_STRING,
    video = false,
    overview = EMPTY_STRING,
    releaseDate = EMPTY_STRING,
    raiting = 0.0,
    tagline = EMPTY_STRING,
)

fun getMovieResponse() = MoviesResponse(EMPTY_INT, listOf(getMovie(), getMovie()), EMPTY_INT, EMPTY_INT)

fun getCast() = Cast(EMPTY_STRING, EMPTY_STRING)