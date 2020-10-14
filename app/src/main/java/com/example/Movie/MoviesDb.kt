package com.example.Movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//import com.example.Movie.MoviesDb.Companion.TABLE_NAME
import com.google.gson.annotations.SerializedName




@Entity
data class MoviesDb constructor(
    @PrimaryKey
   var id: Long = 0,
   var title: String,
    val overview: String="",
 val posterPath: String="",
    val backdropPath: String="",
  val rating: Float=0.0F,
   val releaseDate: String="",
 val adult: String="",
   val runtime: Long=0,
   val origlang: String="",
 val budget: String="",
  val genres: String="")

fun List<MoviesDb>.asDomainModel(): List<Movies> {
    return map {
        Movies(
            id = it.id,
            title = it.title,
            overview = it.overview,
            posterPath = it.posterPath,
            backdropPath = it.backdropPath,
              rating=it.rating,
        releaseDate = it.releaseDate,
        adult = it.adult,
        runtime = it.runtime,
        origlang=it.origlang,
        budget = it.budget,
        genres=it.genres)
    }
}
