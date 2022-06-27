package com.irmiwiz.popcornflix.domain.model

import com.irmiwiz.popcornflix.core.helper.EMPTY_INT
import com.irmiwiz.popcornflix.core.helper.EMPTY_STRING
import com.irmiwiz.popcornflix.data.model.MovieApi
import kotlin.math.log

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
        backDropPath = backdrop_path ?: EMPTY_STRING,
        id = id ?: EMPTY_INT,
        originalTitle = original_title ?: EMPTY_STRING,
        title = title ?: EMPTY_STRING,
        posterPath = poster_path ?: EMPTY_STRING,
        logoPath = logo_path ?: EMPTY_STRING,
        video = video,
        overview = overview ?: EMPTY_STRING,
        releaseDate = release_date ?: EMPTY_STRING,
        raiting =  vote_average ?: 0.0,
        tagline = tagline ?: EMPTY_STRING
    )
}
