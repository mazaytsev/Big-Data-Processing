package practice.practice2

import org.apache.spark.sql.SparkSession


object TweetsAnalyzing {

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\dev\\winutils\\bin")

    val jsonFile = "data/sampletweets.json"

    //Initialize SparkSession
    val sparkSession = SparkSession
      .builder()
      .appName("spark-sql-basic")
      .master("local[*]")
      .getOrCreate()

    //Read json file to DF
    val tweets = sparkSession.read.json(jsonFile)

    //Print the structure (the scheme) of the json file
    tweets.printSchema()

    //Register the DataFrame as a SQL temporary view
    tweets.createOrReplaceTempView("tweets")

    //Count the most active languages
    sparkSession.sql("SELECT actor.languages, count(actor.languages) " +
      "FROM tweets " +
      "WHERE actor.languages IS NOT NULL " +
      "GROUP BY (actor.languages) ORDER BY count(actor.languages) DESC").show()

    //Find the 10 most popular Hashtags
    sparkSession.sql("SELECT twitter_entities.hashtags.text " +
      "FROM tweets " +
      "WHERE twitter_entities.hashtags IS NOT NULL AND size(twitter_entities.hashtags) != 0 ").show(100)

    //Get Top devices used among all Twitter users
    sparkSession.sql("SELECT generator.displayName, count(generator.displayName) " +
      "FROM tweets " +
      "WHERE generator.displayName IS NOT NULL " +
      "GROUP BY (generator.displayName) ORDER BY count(generator.displayName) DESC").show()
  }
}
