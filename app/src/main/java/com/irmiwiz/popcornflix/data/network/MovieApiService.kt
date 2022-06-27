package com.irmiwiz.popcornflix.data.network

import com.irmiwiz.popcornflix.data.model.CastResponseApi
import com.irmiwiz.popcornflix.data.model.MovieApi
import com.irmiwiz.popcornflix.data.model.MoviesResponseApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("$MOVIE_INFO{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieApi>

    @GET(DISCOVER_MOVIE)
    suspend fun getListOfMovies(
        @Query("api_key") apiKey: String,
        @Query("sort_by") sortBy: String,
        @Query("include_video") includeVideo : Boolean = true,
        @Query("page") page : Int
    ): Response<MoviesResponseApi>

    @GET(SEARCH_MOVIE)
    suspend fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") sortBy: String
    ): Response<MoviesResponseApi>

    @GET(MOVIE_CAST)
    suspend fun getCast(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ):Response<CastResponseApi>

    companion object {
        const val MOVIE_INFO = "movie/"
        const val DISCOVER_MOVIE = "discover/movie/"
        const val SEARCH_MOVIE = "search/movie/"
        const val MOVIE_CAST = "movie/{movie_id}/credits"
    }
}