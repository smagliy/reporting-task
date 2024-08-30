package com.movieratings.services.parser

import com.movieratings.models.MovieTitlesModel
import com.movieratings.services.log.LogService
import scala.util.Try

class MovieTitlesParser extends TxtParser[MovieTitlesModel]  {
  def parseToModel(data: List[Array[String]]): Try[List[MovieTitlesModel]] = {
    LogService.logger.info("Parsing list of arrays to model MovieTitlesModel")
    Try(for (
      row <- data
    ) yield MovieTitlesModel(
      movieID = row(0).toInt, yearOfRelease = row(1), title = row(2))
    )
  }
}
