# Practice 6: Spark MLLib - Movie Recomendation System

In this practice we will develop movie recomendation system using Spark MLlib ALS algorithm.The common workflow will have the following steps:
1. Load the sample data.
2. Parse the data into the input format for the ALS algorithm.
3. Split the data into two parts: one for building the model and one for testing the model.
4. Run the ALS algorithm to build/train a user product matrix model.
5. Make predictions with the training data and observe the results.
6. Test the model with the test data

### 1. Load MovieLens Data

Load MovieLens Data to SparkSQLContext and prepare the following DataFrames: movies, ratings, personalRatings.
Please copy personalRatings.txt.template and replace <?> with your ratings.
For example:
```sh
0::1::0::1400000000::Toy Story (1995)
0::780::5::1400000000::Independence Day (a.k.a. ID4) (1996)
0::590::3::1400000000::Dances with Wolves (1990)
0::1210::2::1400000000::Star Wars: Episode VI - Return of the Jedi (1983)
0::648::2::1400000000::Mission: Impossible (1996)
```
### 2. Prepare training and testing sets

Split the ratings into two non-overlapping subsets, named training, test.
```sh
 // Split dataset into training and testing parts
 val Array(training, test) = ratingWithMyRats.randomSplit(Array(0.5, 0.5))
```
### 3. Initialize ALS() function and Train Model
Initialize ALS object and define names of corresponding colums of your dataframe:
```sh
   // Build the recommendation model using ALS on the training data
    val als = new ALS()
      .setMaxIter(3)
      .setRegParam(0.01)
      .setUserCol("userId")
      .setItemCol("movieId")
      .setRatingCol("rating")

    //Get trained model
    val model = als.fit(training)
```
### 4. Calculate predictions on the test dataset:
```sh
  val predictions = model.transform(test).na.drop
```

### 5. Evaluate the model by RMSE

```sh
   val evaluator = new RegressionEvaluator()
      .setMetricName("rmse")
      .setLabelCol("rating")
      .setPredictionCol("prediction")
   val rmse = evaluator.evaluate(predictions)

   println(s"Root-mean-square error = $rmse")
```
### 6. Add your personal ratings
Load your personalRating into dataframe and union with the original rating dataframe:

```sh
val ratingWithMyRats = ratings.union(myRating)

//Get My Predictions
val myPredictions = model.transform(myRating).na.drop
```