package practice.practice1

import org.apache.spark.{SparkConf, SparkContext}


object TaskA {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\dev\\winutils\\bin")

    val conf = new SparkConf()
    conf.setAppName("TaskA")
    conf.setMaster("local[2]")

    val sc = new SparkContext(conf)
    val inputFile = "data/products.csv"

    val fileRDD = sc.textFile(inputFile)
    val wordsRDD = fileRDD.flatMap(s => s.split(" ")).map(s => (s, 1))
    val wordsCount = wordsRDD.reduceByKey((a, b) => a + b)

    wordsCount.foreach(map => {
      println("word = " + map._1 + "; count = " + map._2)
    })
  }
}