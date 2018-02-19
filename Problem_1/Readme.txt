Problem Statement

1. For each customer and product, compute (1) the customer's average sale of this product, (2) the customer’s average sale of the other products (3) the average sale of the product for the other customers.


The image in the OutputImage.png is just for illustration. They are not actual values.


How to run this code in Java:
  
 1: Install jdk on your machine
 2: Install Eclipse version for your computer and add the path to the jdk file bin. For example:C:\Program Files\Java\jdk1.8.0_65\bin
 3: Install the JDBC driver for postgreSQL on your machine. It will be downloaded as a .jar file
 4: Create a Java Application in Eclipse with the class file name name JavaPart1
 5: Add the .jar file to the library for the application 
 6: Copy paste the entire code in Eclipse IDE.
 	Edit the username, password and url in the code according to your machine
 7: Run the program to display the results of the query


Justification for type of data structure used:
  Data Structure used: Hashmaps
  Reason: Easy to map values. Hashmaps are key value pairs. It's similar to using the database queries 
  as the keys can act like primary keys due to their uniqueness.
  
 How the program works:
  1> Considers the cust, prod  as a unique key for this hashmap
  depending on this key it takes individual vales from the sales table
  2> In the first pass we update the values of key which is cust and prod combination and find the average for it
  3> in the next pass we get the we check the values for cust and 
	update the values for other cust and do the same for prod
   
  	
  4>Display the results in the form of key-value pair.
  	
 

