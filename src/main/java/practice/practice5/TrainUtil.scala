package practice.practice5

import org.apache.spark.mllib.feature.HashingTF


object TrainUtil {

  def featurize(s: String) = {
    val numFeatures = 1000
    val tf = new HashingTF(numFeatures)
    tf.transform(s.sliding(2).toSeq)
  }

}