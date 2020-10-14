package com.example.Movie

import com.google.gson.annotations.SerializedName

data class Movies (


    @SerializedName("id") val id: Long=0,
    @SerializedName("title") val title: String="",
    @SerializedName("overview") val overview: String="",
    @SerializedName("poster_path") val posterPath: String="",
    @SerializedName("backdrop_path") val backdropPath: String="",
    @SerializedName("vote_average") val rating: Float=0.0F,
    @SerializedName("release_date") val releaseDate: String="",
    @SerializedName("adult") val adult: String="",
    @SerializedName("runtime") val runtime: Long=0,
    @SerializedName("original_language") val origlang: String="",
    @SerializedName("budget") val budget: String="",
    @SerializedName("genres") val genres: String=""
    )