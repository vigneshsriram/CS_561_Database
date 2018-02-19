
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
public class JavaPart2{	
	
//create all possible combinations in  first scan and then update all 
	//other values
	public static void main(String[] args)
	{
		String usr ="userName";
		String pwd ="password";
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
			Map<String , ArrayList<String>> multimap = new HashMap<String , ArrayList<String>>();

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
				
				// Fill the values if the values are not in the hashmap
				if(multimap.get(rs.getString("cust") + "+" + multimap.get(rs.getString("prod")) + "+" + Quater) == null) {
					String key1 = rs.getString("cust")+ "+" + rs.getString("prod")+"+Q1";
					String key2 = rs.getString("cust")+ "+" + rs.getString("prod")+"+Q2";
					String key3 = rs.getString("cust")+ "+" + rs.getString("prod")+"+Q3";
					String key4 = rs.getString("cust")+ "+" + rs.getString("prod")+"+Q4";
					ArrayList<String> customer2 = new ArrayList<String>();
					customer2.addAll(Arrays.asList(null , null));
					multimap.put(key1 , customer2);
					multimap.put(key2 , customer2);
					multimap.put(key3 , customer2);
					multimap.put(key4 , customer2);
				}				
				if(multiMap.get(rs.getString("cust")+ "+" + rs.getString("prod") + "+" + Quater) == null) {
					String key = rs.getString("cust")+ "+" + rs.getString("prod")+"+"+Quater;
					String avg1 = rs.getString("quant"); // This is normal average
					String count1 = "1"; // This will hold the count 
					ArrayList<String> customer = new ArrayList<String>();
					customer.addAll(Arrays.asList(avg1,count1));
					multiMap.put(key, customer);


				}
				else {
					
					// If the values are present in the hashmap
				
					String key = (rs.getString("cust")+ "+" + rs.getString("prod") + "+" + Quater);
					
					int newAvg = Integer.parseInt(multiMap.get(key).get(0)) + Integer.parseInt(rs.getString("quant"));
					String newCount = multiMap.get(rs.getString("cust")+ "+" + rs.getString("prod") + "+" + Quater).get(1);
					int newcount = Integer.parseInt(newCount) + 1;
					
					multiMap.get(key).set(0, Integer.toString(newAvg));
					multiMap.get(key).set(1, Integer.toString(newcount));

				}
			}
			//System.out.println(map2.size());
			
			for (Entry<String, ArrayList<String>> entry : multiMap.entrySet()) {
			    
			    float newAvg = Integer.parseInt(entry.getValue().get(0)) / Integer.parseInt(entry.getValue().get(1));
			    String newAvg1 = Integer.toString(Math.round(newAvg));
			    // Put this updated value in the map
			    ArrayList<String> a = entry.getValue();
			    a.set(0, newAvg1);
			}
			
			System.out.println("Values have been rounded off");
			System.out.println(String.format("%-12s %-12s %-12s %-15s %-15s", "CUSTOMER","PRODUCT","QUARTER","BEFORE_AVG","AFTER_AVG"));
			System.out.println(String.format("%-12s %-12s %-12s %-15s %-15s", "========","=======","=======","==========","========="));
			
			
			for (Entry<String, ArrayList<String>> entry : multimap.entrySet()) {
				String part = entry.getKey();
				String[] parts = part.split("\\+"); //Splits the key to get back cust and prod
	            String Cust = parts[0];
	            String prod = parts[1];
	            String quat = parts[2];
	            // display for Q1
	            if (quat.equals("Q1")) {
	            	
	            	String before = null;
	            	String after = null;
	            	if(multiMap.get(Cust+ "+" + prod + "+" + "Q2") != null)
	            	 after = multiMap.get(Cust+ "+" + prod + "+" + "Q2").get(0);
	            	
	            	System.out.println(String.format("%-12s %-12s %-7s %15s %14s", Cust,prod,quat,before,after));
	            	
	            }
	         // display for Q2
	            else if(quat.equals("Q2")) {
	            	String before = null;
	            	String after = null;
	            	if(multiMap.get(Cust+ "+" + prod + "+" + "Q1")!= null)
	            	 before =multiMap.get(Cust+ "+" + prod + "+" + "Q1").get(0);
	            	if(multiMap.get(Cust+ "+" + prod + "+" + "Q3") != null)
	            	 after = multiMap.get(Cust+ "+" + prod + "+" + "Q3").get(0);
	            	System.out.println(String.format("%-12s %-12s %-7s %15s %14s", Cust,prod,quat,before,after));
	            	
	            }
	         // display for Q3
	            else if(quat.equals("Q3")) {
	            	String before = null;
	            	String after = null;
	            	if(multiMap.get(Cust+ "+" + prod + "+" + "Q2")!= null)
	            	 before = multiMap.get(Cust+ "+" + prod + "+" + "Q2").get(0);
	            	if(multiMap.get(Cust+ "+" + prod + "+" + "Q4")!= null)
	            	 after = multiMap.get(Cust+ "+" + prod + "+" + "Q4").get(0);
	            	System.out.println(String.format("%-12s %-12s %-7s %15s %14s", Cust,prod,quat,before,after));
	            	
	            }
	         // display for Q4
	            else if(quat.equals("Q4")) {
	            	String before = null;
	            	String after = null;
	            	if(multiMap.get(Cust+ "+" + prod + "+" + "Q3")!= null)
	            	 before = multiMap.get(Cust+ "+" + prod + "+" + "Q3").get(0);
	            	System.out.println(String.format("%-12s %-12s %-7s %15s %14s", Cust,prod,quat,before,after));
	            	
	            }
	            
			    
			}
			
						
		}

		catch(SQLException e)
		{
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}

	}

}


