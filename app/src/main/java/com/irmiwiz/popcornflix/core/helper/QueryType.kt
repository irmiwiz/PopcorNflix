package com.irmiwiz.popcornflix.core.helper

enum class QueryType(val value: String) {
    MOST_POPULAR("popularity.desc"),
    MOST_RECENT("release_date.desc"),
    BEST_RATED("vote_average.desc")
}