package practice.practice3

import org.apache.spark.sql.types._


object RetailData {
  val dataSchema = StructType(Array(
    StructField("InvoiceNo", StringType, nullable = true),
    StructField("StockCode", StringType, nullable = true),
    StructField("Description", StringType, nullable = true),
    StructField("Quantity", IntegerType, nullable = true),
    StructField("InvoiceDate", StringType, nullable = true),
    StructField("UnitPrice", DoubleType, nullable = true),
    StructField("CustomerID", IntegerType, nullable = true),
    StructField("Country", StringType, nullable = true)))
}