package com.movieratings.models

case class MovieTitlesModel(
                           movieID: Int,
                           yearOfRelease: String,
                           title: String
                           ) extends BasicModel {
  override def arrayParameters: Array[Any] =
    Array(movieID, yearOfRelease, title)
  override def arrayNameParameters: Array[String] =
    Array("movieID", "yearOfRelease", "title")

}
