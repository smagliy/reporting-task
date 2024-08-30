package com.movieratings.services.generator

import com.movieratings.models.{MovieTitlesModel, ReportModel, TrainingSetModel}
import com.movieratings.services.log.LogService
import com.movieratings.services.parser.TrainingSetParser
import com.movieratings.services.reader.Reader
import com.movieratings.services.utils.FileLister

import java.nio.file.Path
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


class ReportGenerationService(movieTitles: List[MovieTitlesModel], directoryTrainingSet: String) {

  def generateReport(): List[ReportModel] = {
    val filePaths: List[Path] = FileLister.listFiles(directoryTrainingSet)
    LogService.logger.info(s"Processing each file in directory $directoryTrainingSet concurrently")
    val futureReports: List[Future[List[ReportModel]]] = filePaths.map { path =>
      Future(processFile(path))
    }
    val reports: List[ReportModel] = Await.result(Future.sequence(futureReports), Duration.Inf).flatten
    reports
  }

  private def parseTrainingSet(path: Path): List[TrainingSetModel] = {
    new TrainingSetParser().parseToModel(Reader.readTxtFile(path.toFile)) match {
      case Success(value) =>
        LogService.logger.info(s"Successfully parsed ${value.size} records from file: $path")
        value
      case Failure(exception) =>
        LogService.logger.error(s"Failed to parse TrainingSet data from file: $path due to: ${exception.getMessage}")
        List.empty
    }
  }

  private def processFile(path: Path): List[ReportModel] = {
    val fileName = path.getFileName.toString
    LogService.logger.info(s"Processing file: $fileName")
    val trainingSet = parseTrainingSet(path)
    if (trainingSet.nonEmpty) {
      LogService.logger.info(s"Processing ${trainingSet.size} records from file: $fileName")
      trainingSet.headOption.map { firstRecord =>
        compareDataToModel(firstRecord.movieID, trainingSet)
      }.getOrElse {
        LogService.logger.warn(s"No valid records found in file: $fileName")
        List.empty[ReportModel]
      }
    } else {
      LogService.logger.warn(s"No records to process in file: $fileName")
      List.empty[ReportModel]
    }
  }

  private def compareDataToModel(movieID: Int, trainingSet: List[TrainingSetModel]): List[ReportModel] = {
    LogService.logger.info(s"Comparing TrainingSet data with MovieTitles using movieID: $movieID")
    movieTitles.find(_.movieID == movieID).map { movie =>
      val totalRating = trainingSet.map(_.rating).sum
      val averageRating = totalRating.toDouble / trainingSet.length
      val numberOfReviews = trainingSet.length
      ReportModel(
        movieTitle = movie.title,
        yearOfRelease = movie.yearOfRelease,
        averageRating = averageRating,
        numberOfReviews = numberOfReviews
      )
    }.toList
  }

}
