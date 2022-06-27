package com.irmiwiz.popcornflix.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieApi(
    @SerialName("backdrop_path")
    var backDropPath: String? = null,

    @SerialName("id")
    var id: Int? = null,

    @SerialName("original_title")
    var originalTitle: String? = null,

    @SerialName("title")
    var title: String? = null,

    @SerialName("poster_path")
    var posterPath: String? = null,

    @SerialName("logo_path")
    var logoPath: String? = null,

    @SerialName("video")
    var video: Boolean = false,

    @SerialName("overview")
    var overview: String? = null,

    @SerialName("release_date")
    var releaseDate: String? = null,

    @SerialName("vote_average")
    var raiting: Double?  = null,

    @SerialName("tagline")
    var tagline: String? = null,
)
