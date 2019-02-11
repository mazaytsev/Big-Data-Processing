package practice.practice5

import com.google.gson.Gson
import org.apache.log4j.{Level, Logger}
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author alexander.leontyev
  *
  *         Consumer API keys
  *         KNVTwiYajvHab5NBqBWSWepde (API key)
  *         62eWYe5bBZlf09mXzpdgtOAzSBm7FxlkoZCgCYmRblpTOSI4wu (API secret key)
  *         863078539347861504-eWeovitnbZ2g8eNinenoMTz1PTfMbOY (Access token)
  *         UP1tsTaFuVXTmDJ3nIxGYo0mhqt3ybOuClEQ1V4oTWvbT (Access token secret)
  */

object TwitterStreamAnalyze {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
    val conf = new SparkConf()
    conf.setAppName("spark-sreaming")
    conf.setMaster("local[2]")
    val sc = new SparkContext(conf)

    val ssc = new StreamingContext(sc, Seconds(1))

    //Configure your Twitter credentials
    val apiKey = "KNVTwiYajvHab5NBqBWSWepde"
    val apiSecret = "62eWYe5bBZlf09mXzpdgtOAzSBm7FxlkoZCgCYmRblpTOSI4wu"
    val accessToken = "863078539347861504-eWeovitnbZ2g8eNinenoMTz1PTfMbOY"
    val accessTokenSecret = "UP1tsTaFuVXTmDJ3nIxGYo0mhqt3ybOuClEQ1V4oTWvbT"

    System.setProperty("twitter4j.oauth.consumerKey", apiKey)
    System.setProperty("twitter4j.oauth.consumerSecret", apiSecret)
    System.setProperty("twitter4j.oauth.accessToken", accessToken)
    System.setProperty("twitter4j.oauth.accessTokenSecret", accessTokenSecret)

    //Create Twitter Stream
    val stream = TwitterUtils.createStream(ssc, None)
    val tweets = stream.map(t => t.getText)

    tweets.print()
    tweets.saveAsTextFiles("data/tweets", "json")

    //Get hashtag for analyze
    val hashtags = tweets.flatMap(s => s.split(" ")).filter(w => w.startsWith("#")).map(hashtag => (hashtag, 1))
    val result = hashtags.reduceByKey(_ + _)

    val tweetsJson = stream.map(new Gson().toJson(_))

    val numTweetsCollect = 10000L
    var numTweetsCollected = 0L

    //Save tweets in json
    tweetsJson.foreachRDD((rdd, time) => {
      val count = rdd.count()
      if (count > 0) {
        val outputRDD = rdd.coalesce(1)
        outputRDD.saveAsTextFile("data/tweets/" + time)
        numTweetsCollected += count
        if (numTweetsCollected > numTweetsCollect) {
          System.exit(0)
        }
      }
    })

    ssc.start()
    ssc.awaitTermination()
  }
}
