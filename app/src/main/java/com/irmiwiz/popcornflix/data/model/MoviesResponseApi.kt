package com.irmiwiz.popcornflix.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponseApi (
    @SerialName("page")
    var page : Int? = null,

    @SerialName("results")
    var movies: List<MovieApi>? = null,

    @SerialName("total_results")
    var totalResults : Int? = null,

    @SerialName("total_pages")
    var totalPages : Int? = null,
)