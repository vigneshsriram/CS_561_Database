


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
public class JavaPart3{	
	
//create all possible combinations in  first scan and then update all 
	//other values
	public static void main(String[] args)
	{	
		//Change the usr and pwd according to your database
		String usr ="userName";
		String pwd ="Password";
		String url ="jdbc:postgresql://localhost:5432/postgres";
		

		try
		{
			Class.forName("org.postgresql.Driver");
			//System.out.println("Success loading Driver!");
		}

		catch(Exception e)
		{
			System.out.println("Fail loading Driver!");
			e.printStackTrace();
		}

		try
		{
			Connection conn = DriverManager.getConnection(url, usr, pwd);
			//System.out.println("Success connecting server!");

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Sales");
			Map<String,ArrayList<String>> multiMap = new HashMap<String,ArrayList<String>>();

			while (rs.next())
			{
				//System.out.println(rs.getString("cust") + " " + rs.getString("prod") + " " + rs.getString("day") + " " +
				//   	rs.getString("month") + " " + rs.getString("year") + " " + rs.getString("quant"));
				String Quater = "";
				int month = Integer.parseInt(rs.getString("month"));
								
				if(month >= 1 && month <=3 ) {
					Quater = "Q1";
				}
				if(month >= 4 && month <=6 ) {
					Quater = "Q2";
				}
				if(month >= 7 && month <=9 ) {
					Quater = "Q3";
				}
				if(month >= 10 && month <=12 ) {
					Quater = "Q4";
				}
				
				if(multiMap.get(rs.getString("cust")+ "+" + rs.getString("prod") + "+" + Quater) == null) {
					
					//Value is not present in the hashmap
					String key = rs.getString("cust")+ "+" + rs.getString("prod")+"+"+Quater;
					
					String avg1 = rs.getString("quant"); // This is normal average
					String min = rs.getString("quant");
					String count1 = "1"; // This will hold the count 
					String beforeTot = "0";
					String afterTot = "0";
					ArrayList<String> customer = new ArrayList<String>();
					customer.addAll(Arrays.asList(avg1,count1,min,beforeTot,afterTot));
					multiMap.put(key, customer);

				}
				else {
					String key = (rs.getString("cust")+ "+" + rs.getString("prod") + "+" + Quater);
					//System.out.println(key + multiMap.get(key));
					int newAvg = Integer.parseInt(multiMap.get(key).get(0)) + Integer.parseInt(rs.getString("quant"));
					String newCount = multiMap.get(key).get(1);
					int newcount = Integer.parseInt(newCount) + 1;
					
					int newquant = Integer.parseInt(rs.getString("quant")); 
					//System.out.println(newquant);
					String oldMin = multiMap.get(key).get(2); // this gets the stored minimum in the map
					
					int oldmin = Integer.parseInt(oldMin);  // convert the stored mim to int
					if(newquant < oldmin) 
						{oldMin = Integer.toString(newquant);}
					
					multiMap.get(key).set(0, Integer.toString(newAvg));
					multiMap.get(key).set(1, Integer.toString(newcount));
					multiMap.get(key).set(2,oldMin);

				}
			}
			//System.out.println(multiMap);
			
			for (Entry<String, ArrayList<String>> entry : multiMap.entrySet()) {
			    
			    float newAvg = Integer.parseInt(entry.getValue().get(0)) / Integer.parseInt(entry.getValue().get(1));
			    String newAvg1 = Integer.toString(Math.round(newAvg));
			    // Put this updated value in the map
			    ArrayList<String> a = entry.getValue();
			    a.set(0, newAvg1);
			}
			//System.out.println(multiMap);// Values after calculating hte average
			
			ResultSet rset = stmt.executeQuery("SELECT * FROM Sales");
			while (rset.next()) {
				String cust = rset.getString("cust");
				String prod = rset.getString("prod");
				
				String quater = "";
				int month = Integer.parseInt(rset.getString("month"));
								
				if(month >= 1 && month <=3 ) {
					quater = "Q1";
				}
				if(month >= 4 && month <=6 ) {
					quater = "Q2";
				}
				if(month >= 7 && month <=9 ) {
					quater = "Q3";
				}
				if(month >= 10 && month <=12 ) {
					quater = "Q4";
				}
				String Outerkey = cust + "+" + prod + "+" + quater;
				
				if (quater.equals("Q1")) {
	            	//we have to update only after total and before total should be null
	            	//set before total to null
					int currentQuant = rset.getInt("quant");
					if(multiMap.get(cust + "+" + prod + "+" + "Q2")!= null) {
					int average = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q2").get(0));
					int min = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q2").get(2));
					if(currentQuant >= min && currentQuant <= average) {
						int newTotBefore = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q2").get(3)) + 1;
						multiMap.get(multiMap.get(cust + "+" + prod + "+" + "Q2").set(3, Integer.toString(newTotBefore)));
					}
					}
	            	
	            }
	            else if(quater.equals("Q2")) {
	            	
	            	//Updates for Q3 in before total
	            	
	            	if((multiMap.get(cust + "+" + prod + "+" + "Q3") != null )) {
	            		int currentQuant = rset.getInt("quant");
					int average = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q3").get(0));
					int min = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q3").get(2));
					if(currentQuant >= min && currentQuant <= average) {
						int newTotBefore = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q3").get(3)) + 1;
						multiMap.get(multiMap.get(cust + "+" + prod + "+" + "Q3").set(3, Integer.toString(newTotBefore)));
					}
	            	}
					//Updates for Q1 in After Total
					if(multiMap.get(cust + "+" + prod + "+" + "Q1") != null) {
						int currentQuant = rset.getInt("quant");
						int averageA = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q1").get(0));
						int minAfter = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q1").get(2));
						if(currentQuant >= minAfter && currentQuant <= averageA) {
							int newTotAfter = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q1").get(4)) + 1;
							multiMap.get(multiMap.get(cust + "+" + prod + "+" + "Q1").set(4, Integer.toString(newTotAfter)));
						}	
					}	            	
	            }
	            else if(quater.equals("Q3")) {
	            	
	            	//Updates for Q2 in after total
	            	if(multiMap.get(cust + "+" + prod + "+" + "Q2") != null) {
	            		int currentQuant = rset.getInt("quant");
					int average = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q2").get(0));
					int min = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q2").get(2));
					if(currentQuant >= min && currentQuant <= average) {
						int newTotBefore = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q2").get(4)) + 1;
						multiMap.get(multiMap.get(cust + "+" + prod + "+" + "Q2").set(4, Integer.toString(newTotBefore)));
					}
	            	}
					
					//Updates for Q4 in before Total
					if(multiMap.get(cust + "+" + prod + "+" + "Q4") != null) {
						int currentQuant = rset.getInt("quant");
					int averageA = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q4").get(0));
					int minAfter = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q4").get(2));
					if(currentQuant >= minAfter && currentQuant <= averageA) {
						int newTotAfter = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q4").get(3)) + 1;
						multiMap.get(multiMap.get(cust + "+" + prod + "+" + "Q4").set(3, Integer.toString(newTotAfter)));
					}
					}
	            	
	            }
	            else if(quater.equals("Q4")) {
	            	//Updates for Q3 in after total
	            	int currentQuant = rset.getInt("quant");
	            	if(multiMap.get(cust + "+" + prod + "+" + "Q3") != null) {
						int average = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q3").get(0));
						int min = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q3").get(2));
						if(currentQuant >= min && currentQuant <= average) {
							int newTotBefore = Integer.parseInt(multiMap.get(cust + "+" + prod + "+" + "Q3").get(4)) + 1;
							multiMap.get(multiMap.get(cust + "+" + prod + "+" + "Q3").set(4, Integer.toString(newTotBefore)));
						}	            		
	            	}
	            }

			}
			//System.out.println(multiMap);
			//System.out.println("Values have been rounded off");
			System.out.println(String.format("%-12s %-12s %-12s %-15s %-15s", "CUSTOMER","PRODUCT","QUARTER","BEFORE_TOT","AFTER_TOT"));
			System.out.println(String.format("%-12s %-12s %-12s %-15s %-15s", "========","=======","=======","==========","========="));
			
			int counter = 0;
			for (Entry<String, ArrayList<String>> entry : multiMap.entrySet()) {
				
				String part = entry.getKey();
				String[] parts = part.split("\\+"); //Splits the key to get back cust and prod
	            String Cust = parts[0];
	            String prod = parts[1];
	            String quat = parts[2];
	            
	            String after = multiMap.get(Cust+ "+" + prod + "+" + quat).get(4);
	            String before = multiMap.get(Cust+ "+" + prod + "+" + quat).get(3);
	            
	            //Un-comment the below line to display only the values which dont have 0 as before and after
	           if(!(before.equals("0") &&  after.equals("0"))) { 
	            System.out.println(String.format("%-12s %-12s %-7s %15s %14s", Cust,prod,quat,before,after));
	            counter = counter + 1;}}
	           
	            
			//System.out.println(counter); 
			//System.out.println("Uncomment line 258 in the program to remove the values where after and before is 0");
			
						
		}

		catch(SQLException e)
		{
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}

	}

}


