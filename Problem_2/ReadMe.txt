Problem Statement

2. For customer and product, show the average sales before and after each quarter (e.g., for Q2, show average sales of Q1 and Q3.  For “before” Q1 and “after” Q4, display <NULL>).  The “YEAR” attribute is not considered for this query – for example, both Q1 of 2007 and Q1 of 2008 are considered Q1 regardless of the year. 



 

 How to run this code:
  
  
 1: Install jdk on your machine
 2: Install Eclipse version for your computer and add the path to the jdk file bin. For example:C:\Program Files\Java\jdk1.8.0_65\bin
 3: Install the JDBC driver for postgreSQL on your machine. It will be downloaded as a .jar file
 4: Create a Java Application in Eclipse with the class file name name sdap1
 5: Add the .jar file to the library for the application 
 6: Copy paste the entire code in Eclipse IDE(not checked in NetBeans or Notepad).
 	Edit the username, password and url in the code according to your machine
 7: Run the program to display the results of the query

  
 Justification for type of data structure used:
  Data Structure used: Hashmaps
  Reason: Easy to map values. Hashmaps are key value pairs. It's similar to using the database queries 
  as the keys can act like primary keys due to their uniqueness.
  
 How the program works:
  1>Considers the cust, prod, Quarter  as a unique key for this hashmap
  depending on this key it takes individual vales from the sales table
  2>if the key does not exist include it in the hashmap 
  3> calculate the average of each key and store it in the hashmap
  4>while displaying the average display the key of the current 
    and get the values for the quater before and quater after
    5> for each key in the hashmap
    get the key
    if key is "custprodQ1" 
    get the values for "custprodQ2" and dispaly it as after 
    get the value null and display it for before
 	   if key is "custprodQ2" 
    get the values for "custprodQ3" and dispaly it as after 
    get the value custprodQ1 and display it for before
        if key is "custprodQ3" 
    get the values for "custprodQ4" and dispaly it as after 
    get the value custprodQ2 and display it for before
        if key is "custprodQ4" 
    get the values for "custprodQ3" and dispaly it as before 
    get the value null and display it for after
   
  	

