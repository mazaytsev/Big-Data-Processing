# Practice 1: Basic Spark RDD Operations

#### A) Words Count Program
1) Load file product.csv
2) Calculate the frequency of each term in the file using the MapReduce programming paradigm

#### B) RDD[Array[Int]] vs RDD[Int] or Map vs flatMap
Set of integer numbers is given as an input in source file.

1. Create RDD[Array[int]] – where elements of RDD are all numbers of each row in the file and make the following tasks:
   - Calculate the sum of the numbers in each row of file
   - Calculate the sum of numbers of each row, which are multiples of the 5 (number %5 == 0)
   - Find the maximum and minimum elements of each row
   - Find the set of distinct numbers in each row
   
2. Create RDD[int] – where elements of RDDare all numbers of the whole file and make the following tasks:
   - Calculate the sum of the numbers in RDD
   - Calculate the sum of numbers in RDD, which are multiples of the 5 (number %5 == 0)
   - Find the maximum and minimum elements of RDD
   - Find the set of distinct numbers in RDD
   
3. Save results in output file in HDFS.