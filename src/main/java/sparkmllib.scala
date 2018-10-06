import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object SparkMLlib {

  def main(args: Array[String]): Unit = {

    val inputFile = "C:\\Users\\maxza\\Desktop\\sparkmllib\\src\\main\\resources\\retails.csv"
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    //Initialize SparkSession
    val sparkSession = SparkSession
      .builder()
      .appName("spark-read-csv")
      .master("local[*]")
      .getOrCreate();

    //Read file to DF
    val crimes = sparkSession.read
      .option("header", "true")
      .option("delimiter", ",")
      .option("nullValue", "")
      .option("treatEmptyValuesAsNulls", "true")
      .option("inferSchema", "true")
      .csv(inputFile)

    crimes.show(100)
    crimes.printSchema();

  }
}