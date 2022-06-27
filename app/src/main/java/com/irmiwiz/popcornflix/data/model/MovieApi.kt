package com.irmiwiz.popcornflix.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieApi(
    var backdrop_path: String? = null,
    var id: Int? = null,
    var original_title: String? = null,
    var title: String? = null,
    var poster_path: String? = null,
    var logo_path: String? = null,
    var video: Boolean = false,
    var overview: String? = null,
    var release_date: String? = null,
    var vote_average: Double?  = null,
    var tagline: String? = null,
)
