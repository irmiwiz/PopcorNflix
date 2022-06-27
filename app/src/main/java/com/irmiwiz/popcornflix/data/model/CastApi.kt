package com.irmiwiz.popcornflix.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastApi(
    @SerialName("profile_path")
    var profilePath: String? = null,
    @SerialName("name")
    var name: String? = null
)