package com.irmiwiz.popcornflix.domain.model

import com.irmiwiz.popcornflix.core.helper.EMPTY_INT
import com.irmiwiz.popcornflix.core.helper.EMPTY_STRING
import com.irmiwiz.popcornflix.data.model.MovieApi

class Movie(
    var backDropPath: String,
    var id: Int,
    var originalTitle: String,
    var title: String,
    var posterPath: String,
    var logoPath: String,
    var video: Boolean,
    var overview: String,
    var releaseDate: String,
    var raiting: Double,
    var tagline: String,
)

fun MovieApi.toMovie(): Movie {
    return Movie(
        backDropPath = backDropPath ?: EMPTY_STRING,
        id = id ?: EMPTY_INT,
        originalTitle = originalTitle ?: EMPTY_STRING,
        title = title ?: EMPTY_STRING,
        posterPath = posterPath ?: EMPTY_STRING,
        logoPath = logoPath ?: EMPTY_STRING,
        video = video,
        overview = overview ?: EMPTY_STRING,
        releaseDate = releaseDate ?: EMPTY_STRING,
        raiting = raiting ?: 0.0,
        tagline = tagline ?: EMPTY_STRING
    )
}
