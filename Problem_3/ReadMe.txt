Problem Statement

3. For customer and product, count for each quarter, how many sales of the previous and how many sales of the following quarter had quantities between that quarter’s average sale and minimum sale.  Again for this query, the “YEAR” attribute is not considered.

 
 How to run this code:
   
  
 1: Install jdk on your machine
 2: Install Eclipse version for your computer and add the path to the jdk file bin. For example:C:\Program Files\Java\jdk1.8.0_65\bin
 3: Install the JDBC driver for postgreSQL on your machine. It will be downloaded as a .jar file
 4: Create a Java Application in Eclipse with the class file name name Sriram_Vignesh_10430312_JavaPart3
 5: Add the .jar file to the library for the application 
 6: Copy paste the entire code in Eclipse IDE(not checked in NetBeans or Notepad).
 	Edit the username, password and url in the code according to your machine
 7: Run the program to display the results of the query

  
 Justification for type of data structure used:
  Data Structure used: Hashmaps
  Reason: Easy to map values. Hashmaps are key value pairs. It's similar to using the database queries 
  as the keys can act like primary keys due to their uniqueness.
  
 How the program works:
  1> Considers the cust, prod , Quarter as a unique key for this hashmap
  depending on this key it takes individual vales from the sales table
  2> In the first pass we update the values of key which is cust , prod  and quater combination and find the average and min for it
  3> In the next pass we get the we check the values for quant and update the count by 1 if the values lie between the average and min prod
   
  	
 4> Display the results in the form of key-value pair.
  	
 