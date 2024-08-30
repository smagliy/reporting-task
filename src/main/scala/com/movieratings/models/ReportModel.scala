package com.movieratings.models

case class ReportModel(
                      movieTitle: String,
                      yearOfRelease: String,
                      averageRating: Double,
                      numberOfReviews: Int
                      ) extends BasicModel {
  override def arrayParameters: Array[Any] =
    Array(movieTitle, yearOfRelease, averageRating, numberOfReviews)
  override def arrayNameParameters: Array[String] =
    Array("movieTitle", "yearOfRelease", "averageRating", "numberOfReviews")

}
