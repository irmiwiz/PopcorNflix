package com.irmiwiz.popcornflix.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastApi(
    var profile_path: String? = null,
    var name: String? = null
)