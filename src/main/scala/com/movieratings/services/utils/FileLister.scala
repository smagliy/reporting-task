package com.movieratings.services.utils

import com.movieratings.services.log.LogService
import java.nio.file.{Files, Path, Paths}
import scala.collection.JavaConverters._

object FileLister {
  def listFiles(directory: String): List[Path] = {
    val startPath = Paths.get(directory)
    val stream = Files.walk(startPath)
    try {
      LogService.logger.info(s"Trying to found files in directory: $directory")
      stream
        .filter(Files.isRegularFile(_))
        .iterator()
        .asScala
        .toList
    } finally {
      stream.close()
    }
  }
}
