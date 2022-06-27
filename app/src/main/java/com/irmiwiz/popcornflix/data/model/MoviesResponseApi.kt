package com.irmiwiz.popcornflix.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponseApi (
    var page : Int? = null,
    var results: List<MovieApi>? = null,
    var total_results : Int? = null,
    var total_pages : Int? = null,
)