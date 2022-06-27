package com.irmiwiz.popcornflix.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastResponseApi(
    @SerialName("cast")
    var cast: List<CastApi>?  = null
)