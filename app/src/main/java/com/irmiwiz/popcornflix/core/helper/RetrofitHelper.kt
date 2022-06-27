package com.irmiwiz.popcornflix.core.helper

import com.irmiwiz.popcornflix.domain.model.MovieResult
import retrofit2.Response

internal inline fun <T : Any> executeRetrofitRequest(request: () -> Response<T>): MovieResult<T> {
    return try {
        val response = request.invoke()
        return if (response.isSuccessful && response.body() != null) {
            val body = response.body()
            if (body != null) {
                MovieResult.Success(body)
            } else {
                MovieResult.Error("Empty body found in this request")
            }
        } else {
            val errorBody = response.errorBody()
            val errorText = if (errorBody == null) "Error body null" else errorBody.string()
            MovieResult.Error(errorText, response.code())
        }
    }
    catch (ex: Exception) {
        MovieResult.Error(ex.message)
    }
}

fun <Api : Any, Data : Any> handleResultRetrofit(
    result: MovieResult<Api>,
    onSuccess: (Api) -> Data
): MovieResult<Data> {
    return when (result) {
        is MovieResult.Success -> MovieResult.Success(onSuccess.invoke(result.data))
        is MovieResult.Error -> {
            if(result.exception?.contains("Unable to resolve host") == true) {
                MovieResult.InternetConnectionError
            } else {
                result
            }
        }
        else -> MovieResult.Error("Generic error")
    }
}