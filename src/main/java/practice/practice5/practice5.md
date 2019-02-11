# Practice 5: Spark Streaming - Twitter Analysis on Real Time

The main goal of this exercise to create a basic spark streaming application, which process tweets in real time from Twitter.

#### Part 1: Basics
##### A. Preliminaries
1. Learn the basics of spark streaming (read the following parts)
2. Get Twitter Credentials Keys (read this guide)

##### B. Create Basic Spark Twitter Streaming Application
1) Create your first spark streaming application that prints a sample of the tweets it receives from Twitter on real time, follow this tutorial
2) Afer you got a twitter stream, do the following tasks:
- Get the stream of hashtags from the stream of tweets: To get the hashtags from the status string, we need to identify only those words in the message that start with “#”.
- Find the top 10 hashtags based on their counts

##### C. Collect a Dataset of Tweets
Follow this tutorial to save twitter stream in files

#### Part 2: Twitter Streaming Language Classifier
This section is aimed to train a language classifier using the text in the Tweets.

##### A. Prepare data

1. Load file which was gained in the previous steps into SparkSQL Context and extract the body of tweets, we need to get RDD[string] that represent all tweets from the file
2. After we need to featurize the Tweet text. MLLib has a HashingTF class that does that:
  ```sh
  //Featurize Function
  def featurize(s: String) = {
    val numFeatures = 1000
    val tf = new HashingTF(numFeatures)
    tf.transform(s.sliding(2).toSeq)
  }
  ```
  
#### B. Train K-means Model
After the featue vector is defined train KMeans model and save it to the output file:
 ```sh
    //Get the features vector
    val features = text.map(s => featurize(s))

    val numClusters = 10
    val numIterations = 40

    // Train KMenas model and save it to file
    val model: KMeansModel = KMeans.train(features, numClusters, numIterations)
    model.save(sparkSession.sparkContext, modelOutput)
  ```
#### C. Apply the Model in Real Time
 ```sh
    println("Initializing the KMeans model...")
    val model = KMeansModel.load(sc, modelInput)
    val langNumber = 3

    val filtered = texts.filter(t => model.predict(featurize(t)) == langNumber)
    filtered.print()
 ```