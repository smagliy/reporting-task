package com.movieratings.services.parser

import com.movieratings.models.TrainingSetModel
import com.movieratings.services.log.LogService

import scala.util.Try

class TrainingSetParser extends TxtParser[TrainingSetModel] {
  def parseToModel(data: List[Array[String]]): Try[List[TrainingSetModel]] = {
    LogService.logger.info("Parsing list of arrays to model TrainingSetModel")
    val movieID: Int = data.head(0).replace(":", "").toInt
    Try(for (
      row <- data if row.length >= 3
    ) yield TrainingSetModel(
      movieID = movieID, customerID = row(0).toInt, rating = row(1).toInt, date = row(2)
    ))
  }

}
