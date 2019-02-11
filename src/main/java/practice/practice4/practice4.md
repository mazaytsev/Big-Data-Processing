# Practice 4: Spark MLlib - Logistic Regression
The main goal of this practice is to learn Logistic Regression Model on the diabets.csv data.

#### Attribute Information:
1. Number of times pregnant
2. Plasma glucose concentration a 2 hours in an oral glucose tolerance test
3. Diastolic blood pressure (mm Hg)
4. Triceps skin fold thickness (mm)
5. 2-Hour serum insulin (mu U/ml)
6. Body mass index (weight in kg/(height in m)^2)
7. Diabetes pedigree function
8. Age (years)
9. Class variable (0 or 1)


#### Task
1. Load diabets.csv data into Spark's DataFrame and transform features into features are transformed and put into Feature Vectors (by using VectorAssembler)
2. Once the set of features defined, split it to the training and testing datasets
3. Train Logistic Regression on Training portion of data
4. Predict outcome variable (diabet) on Testing dataset
5. Test the model