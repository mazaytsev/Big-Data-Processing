# Practice 3: Spark MLlib - Market Basket Analysis via FPGrowth

#### Input Dataset
The main goal of this practice is to derive purchase patterns in Online Retail Data Set. This is a transnational data set which contains all the transactions occurring between 01/12/2010 and 09/12/2011 for a UK-based and registered non-store online retail. The company mainly sells unique all-occasion gifts. Many customers of the company are wholesalers.

#### Attribute Information:

| Name | Description |
| ------ | ------ |
| InvoiceNo: | Invoice number. Nominal, a 6-digit integral number uniquely assigned to each transaction. If this code starts with letter 'c', it indicates a cancellation.|
| StockCode: | Product (item) code. Nominal, a 5-digit integral number uniquely assigned to each distinct product.|
| Description: | Product (item) name. Nominal.|
| Quantity: | The quantities of each product (item) per transaction. Numeric.|
| InvoiceDate: | Invice Date and time. Numeric, the day and time when each transaction was generated.|
| UnitPrice: | Unit price. Numeric, Product price per unit in sterling.|
|CustomerID: | Customer number. Nominal, a 5-digit integral number uniquely assigned to each customer.|
|Country: | Country name. Nominal, the name of the country where each customer resides.|

```sh
InvoiceNo;StockCode;Description;Quantity;InvoiceDate;UnitPrice;CustomerID;Country
536365;85123A;WHITE HANGING HEART T-LIGHT HOLDER;6;01.12.2010 8:26;2,55;17850;United Kingdom
536365;71053;WHITE METAL LANTERN;6;01.12.2010 8:26;3,39;17850;United Kingdom
536365;84406B;CREAM CUPID HEARTS COAT HANGER;8;01.12.2010 8:26;2,75;17850;United Kingdom
536365;84029G;KNITTED UNION FLAG HOT WATER BOTTLE;6;01.12.2010 8:26;3,39;17850;United Kingdom
536365;84029E;RED WOOLLY HOTTIE WHITE HEART.;6;01.12.2010 8:26;3,39;17850;United Kingdom
```

#### Task
1. Load Online Retail Data Set into Spark's DataFrame and define the set of transactions as a set of products which was bought by one customer. The set of transactions is a set of products' ID (StockCode)
2. Once the set of transactions are defined apply FPGrowth algorithm in order to find purchase frequent patterns.
3. Please create a dictionary DataFrame in the following view _[StockCode, Description]_ in order to print patterns in human-readable format. For example: _[RED WOOLLY HOTTIE WHITE HEART]_ => _[CREAM CUPID HEARTS COAT HANGER]_
4. Evalaute scalability of FPGrowth algorithm in Spark cluster environment.