package practice.practice6

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.{DataFrameReader, SparkSession}

/**
  * @author alexander.leontyev
  */
object MovieRecomendationSystem {

  case class Rating(userId: Int, movieId: Int, rating: Float, timestamp: Long)

  case class Movie(movieId: Int, movieName: String, rating: Float)

  def parseRating(str: String): Rating = {
    val fields = str.split("::")
    Rating(fields(0).toInt, fields(1).toInt, fields(2).toFloat, fields(3).toLong)
  }

  def getDataFrameReader(sparkSession: SparkSession): DataFrameReader = {
    sparkSession.read
      .option("header", "true")
      .option("delimiter", ";")
      .option("nullValue", "")
      .option("treatEmptyValuesAsNulls", "true")
      .option("inferSchema", "true")
  }

  def parseMovie(str: String): Movie = {
    val fields = str.split("::")
    Movie(fields(0).toInt, fields(1), 0)
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    //Initialize spark configurations
    val spark = SparkSession
      .builder()
      .appName("market-basket-problem")
      .master("local[*]")
      .config("spark.executor.memory", "8g")
      .config("spark.driver.memory", "8g")
      .getOrCreate()
    val inputFile = "data/medium/"

    import spark.implicits._
    val movieFile = "movies.dat"
    val ratingFile = "ratings.dat"
    val personalRatingsFile = "personalRatings.txt"

    val movies = getDataFrameReader(spark)
      .textFile(inputFile + movieFile).map(parseMovie).toDF()
    val ratings = getDataFrameReader(spark)
      .textFile(inputFile + ratingFile).map(parseRating).toDF()
    val personalRatings = getDataFrameReader(spark).textFile(inputFile + personalRatingsFile)
      .map(parseRating)
      .toDF()

    val numRatings = ratings.distinct().count()
    val numUsers = ratings.select("userId").distinct().count()
    val numMovies = movies.distinct().count()

    //Get movies dictionary
    println("Got " + numRatings + " ratings from "
      + numUsers + " users on " + numMovies + " movies.")

    val ratingWithRats = ratings.union(personalRatings)
    ratingWithRats.show(10)

    //Split dataset into training and testing parts
    val Array(training, test) = ratingWithRats.randomSplit(Array(0.5, 0.5))

    //Build the recommendation model using ALS on the training data
    val als = new ALS()
      .setMaxIter(20)
      .setRegParam(0.01)
      .setUserCol("userId")
      .setItemCol("movieId")
      .setRatingCol("rating")

    //Get trained model
    val model = als.fit(training)

    //Evaluate Model Calculate RMSE
    val predictions = model.transform(test).na.drop

    val evaluator = new RegressionEvaluator()
      .setMetricName("rmse")
      .setLabelCol("rating")
      .setPredictionCol("prediction")
    val rmse = evaluator.evaluate(predictions)

    println(s"Root-mean-square error = $rmse")

    //Get My Predictions
    val myPredictions = model.transform(personalRatings).na.drop

    //Show your recomendations
    myPredictions.show(10)
    val myMovies = myPredictions.map(
      r => Movie(r.getInt(1), "", r.getFloat(2)))
      .toDF
    myMovies.coalesce(1)
      .write.format("com.databricks.spark.csv")
      .save("sample.csv")
  }

}
