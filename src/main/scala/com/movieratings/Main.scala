package com.movieratings

import com.movieratings.models.ReportModel
import com.movieratings.services.generator.ReportGenerationService
import com.movieratings.services.log.LogService
import com.movieratings.services.parser.MovieTitlesParser
import com.movieratings.services.reader.Reader
import com.movieratings.services.writer.Writer

import java.io.File
import scala.util.Try


object Main {
  private def filterReport(report: List[ReportModel]): List[ReportModel] = {
    LogService.logger.info("Filtering the report to include years " +
      "between 1970 and 1990, with more than 1,000 reviews, " +
      "and ordering the results by average rating and movie title")
    report.filter { movie =>
      val year = Try(movie.yearOfRelease.toInt).getOrElse(0)
      year >= 1970 && year <= 1990 && movie.numberOfReviews > 1000
    }
      .sortBy(movie => (-movie.averageRating, movie.movieTitle))
  }

  def main(args: Array[String]): Unit ={
      if (args.length != 3) {
        LogService.logger.info("Usage: program <movies description file> <training dataset directory> <report output path>")
        sys.exit(1)
      }
      val moviesDescriptionFilePath = new File(args(0))
      val trainingDatasetDirectoryPath = args(1)
      val reportOutputPath = args(2)
      val dataTitles  =  new MovieTitlesParser().parseToModel(Reader.readTxtFile(moviesDescriptionFilePath))
        .getOrElse(List.empty)
      val report = new ReportGenerationService(dataTitles, trainingDatasetDirectoryPath).generateReport()
      Writer.writeModelToFile(filterReport(report), new File(reportOutputPath))
      LogService.logger.info(s"Report has been successfully generated and saved to $reportOutputPath")
  }
}
