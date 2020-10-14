package com.example.Movie

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "5bd6207de3f86271d1115bfd1b9e8dcc",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "5bd6207de3f86271d1115bfd1b9e8dcc",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}