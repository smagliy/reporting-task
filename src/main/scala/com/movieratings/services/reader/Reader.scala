package com.movieratings.services.reader

import com.movieratings.services.log.LogService
import scala.collection.JavaConverters._
import org.apache.commons.csv.CSVFormat

import java.io.{File, FileReader}


object Reader {
  def readTxtFile(file: File): List[Array[String]] = {
    val reader = new FileReader(file)
    try {
      LogService.logger.info(s"Trying read  records from file: ${file.getName}")
      CSVFormat.DEFAULT.parse(reader).asScala.toList.map(record => record.asScala.toArray)
    } finally {
      reader.close()
    }
  }
}
