package com.movieratings.models

case class TrainingSetModel(
                           movieID: Int,
                           customerID: Int,
                           rating: Int,
                           date: String
                           ) extends BasicModel {
  override def arrayParameters: Array[Any] =
    Array(movieID, customerID, rating, date)
  override def arrayNameParameters: Array[String] =
    Array("movieID", "customerID", "rating", "date")
}
