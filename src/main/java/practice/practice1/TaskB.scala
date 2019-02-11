package practice.practice1

import org.apache.spark.{SparkConf, SparkContext}


object TaskB {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\dev\\winutils\\bin")

    val conf = new SparkConf()
    conf.setAppName("TaskB")
    conf.setMaster("local[2]")

    val sc = new SparkContext(conf)
    val inputFile = "data/numbers.txt"

    val fileRDD = sc.textFile(inputFile)
    val rows = fileRDD.map(_.split(" "))
    val numbers = rows.map(s => s.map(t => t.toInt))

    println("Array of numbers:")
    numbers.map(s => s.mkString(" ")).foreach(println)

    //1

    println("Sum of numbers of each row:")
    val rowsSum = numbers.map(t => t.sum)
    rowsSum.foreach(println)

    println("Sum of numbers of each row, which are multiples of the 5 (number %5 == 0):")
    val rowsDiv5Sum = numbers.map(t => t.filter(_ % 5 == 0).sum)
    rowsDiv5Sum.foreach(println)

    println("Maximum element for each row:")
    val rowsMax = numbers.map(t => t.max)
    rowsMax.foreach(println)

    println("Minimum element for each row:")
    val rowsMin = numbers.map(t => t.min)
    rowsMin.foreach(println)

    println("Set of distinct numbers in each row:")
    val rowsDistinct = numbers.map(_.distinct)
    rowsDistinct.map(s => s.mkString(" ")).foreach(println)

    // 2

    val flatNumbers = fileRDD.flatMap(str => str.split(" ").map(num => num.toInt))

    println("Sum of the numbers in RDD:")
    val sum = flatNumbers.reduce(_ + _)
    println(sum)

    println("Sum of numbers in RDD, which are multiples of the 5 (number %5 == 0):")
    val div5Sum = flatNumbers.filter(_ % 5 == 0).reduce(_ + _)
    println(div5Sum)

    println("Maximum element of RDD:")
    println(flatNumbers.max)

    println("Minimum element of RDD:")
    println(flatNumbers.min)

    println("Set of distinct numbers in RDD:")
    flatNumbers.distinct().foreach(println)
  }
}
