package com.movieratings.services.writer

import com.movieratings.models.BasicModel
import com.movieratings.services.log.LogService
import org.apache.commons.csv.{CSVFormat, CSVPrinter}

import java.io.{File, FileWriter}

object Writer {
  def writeModelToFile(records: List[BasicModel], file: File): Unit = {
    val writer = new FileWriter(file)
    try {
      LogService.logger.info(s"Trying write records from file: ${file.getName}")
      val headers = records.headOption.map(_.arrayNameParameters).getOrElse(Array.empty[String])
      val format = CSVFormat.Builder.create()
        .setHeader(headers: _*)
        .setSkipHeaderRecord(false)
        .build()
      val printer = new CSVPrinter(writer, format)
      records.foreach { record =>
        printer.printRecord(record.arrayParameters.map(_.toString): _*)
      }
    } finally {
      writer.close()
    }
  }
}
