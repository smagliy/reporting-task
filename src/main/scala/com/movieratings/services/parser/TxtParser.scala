package com.movieratings.services.parser

import com.movieratings.models.BasicModel
import scala.util.Try

trait TxtParser[BasicModel] {
  def parseToModel(data: List[Array[String]]): Try[List[BasicModel]]
}
