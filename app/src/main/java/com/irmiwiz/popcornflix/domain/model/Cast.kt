package com.irmiwiz.popcornflix.domain.model

import com.irmiwiz.popcornflix.core.helper.EMPTY_STRING
import com.irmiwiz.popcornflix.data.model.CastResponseApi

data class Cast(
    var profilePath: String,
    var name: String
)

fun CastResponseApi.toCastList(): List<Cast> {
    return cast?.map {
        Cast(profilePath = it.profilePath ?: EMPTY_STRING, name = it.name ?: EMPTY_STRING)
    } ?: emptyList()
}