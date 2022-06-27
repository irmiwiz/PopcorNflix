package com.irmiwiz.popcornflix.domain.model

sealed class MovieResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : MovieResult<T>()
    data class Error(val exception: String?, val errorCode: Int = 0) : MovieResult<Nothing>()
    object InternetConnectionError : MovieResult<Nothing>()
}
