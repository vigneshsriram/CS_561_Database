import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
public class JavaPart1{	
	
//create all possible combinations in  first scan and then update all 
	//other values
	public static void main(String[] args)
	{
		//Put your user name and password here!!
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

			while (rs.next())
			{
				//System.out.println(rs.getString("cust") + " " + rs.getString("prod") + " " + rs.getString("day") + " " +
				//   	rs.getString("month") + " " + rs.getString("year") + " " + rs.getString("quant"));
				if(multiMap.get(rs.getString("cust")+ "+" + rs.getString("prod")) == null) {
					String avg1 = rs.getString("quant"); // This is normal average
					String avg2 = "0"; // This is average for same cust but diff prod
					String avg3 = "0"; // This is average for same prod but diff cust
					String count1 = "1";// For avg1
 					String count2 = "0"; // For avg2
					String count3 = "0";//For avg3
					String key = rs.getString("cust")+ "+" + rs.getString("prod");
					ArrayList<String> customer = new ArrayList<String>();
					customer.addAll(Arrays.asList(avg1,avg2,avg3,count1,count2,count3));
					multiMap.put(key, customer);

				}
				else {
					String Cust = rs.getString("cust");
					String prod = rs.getString("prod");
					
					String KEy = Cust +"+"+ prod;
					
					//Locate the value of the key in the hashmap and update its avg1 and count1
					int newCount = Integer.parseInt(multiMap.get(KEy).get(3)) + 1 ;
					String newCountForAvg1 = Integer.toString(newCount);
					
					int newAvg = Integer.parseInt(multiMap.get(KEy).get(0)) + Integer.parseInt(rs.getString("quant"));
					String newAvg1 = Integer.toString(newAvg);
					
					//update the 2 values in the hashmap
					multiMap.get(Cust + "+" + prod).set(0,newAvg1);
					multiMap.get(Cust + "+" + prod).set(3,newCountForAvg1);
				}
			}	
			
			for (Entry<String, ArrayList<String>> entry : multiMap.entrySet()) {
			    
			    float newAvg = Integer.parseInt(entry.getValue().get(0)) / Integer.parseInt(entry.getValue().get(3));
			    
			    String newAvg1 = Integer.toString(Math.round(newAvg));
			    // Put this updated value in the map
			    ArrayList<String> a = entry.getValue();
			    a.set(0, newAvg1);
			}
			
			//System.out.println(multiMap);
			ResultSet rset = stmt.executeQuery("SELECT * FROM Sales");
			while (rset.next()) {
				String cust = rset.getString("cust");
				String prod = rset.getString("prod");
				int avg = Integer.parseInt(rset.getString("quant"));
				
				for (Entry<String, ArrayList<String>> entry : multiMap.entrySet()) {
					String part = entry.getKey();
					String[] parts = part.split("\\+"); //Splits the key to get back cust and prod
		            String Cust = parts[0];
		            String Prod = parts[1];
		            
		            if(cust.equals(Cust) && (!prod.equals(Prod))) {
		            	//when cust is same and prod is different
		            	//add the quant to avg2 at 1
		            	//increment count by 1 of count2 at 4
		            	String avg2 = entry.getValue().get(1);
		            	int newAvg2 = avg + Integer.parseInt(avg2);
		            	String count2 = entry.getValue().get(4);
		            	int newCount = Integer.parseInt(count2) + 1;
		            	ArrayList<String> a = entry.getValue();
					    a.set(1, Integer.toString(newAvg2));
					    a.set(4, Integer.toString(newCount));
		            	
		            }
		            //System.out.println(prod.equals(Prod) && (!cust.equals(Cust)));
		            if(prod.equals(Prod) && (!cust.equals(Cust))) {
		            	//when cust is diff and prod is same
		            	//add quant to avg2 at 2
		            	//increment count by 1 of count3 at 5
		            	String avg3 = entry.getValue().get(2);
		            	int newAvg3 = avg + Integer.parseInt(avg3);
		            	String count3 = entry.getValue().get(5);
		            	int newCount3 = Integer.parseInt(count3) + 1;
		            	ArrayList<String> a = entry.getValue();
					    a.set(2, Integer.toString(newAvg3));
					    a.set(5, Integer.toString(newCount3));
		       	
		            }
				}
				
			}
			
			for (Entry<String, ArrayList<String>> entry : multiMap.entrySet()) {
			    
			    float newAvg = Integer.parseInt(entry.getValue().get(1)) / Integer.parseInt(entry.getValue().get(4));
			    String newAvg2 = Integer.toString(Math.round(newAvg));
			    float newavg = Integer.parseInt(entry.getValue().get(2)) / Integer.parseInt(entry.getValue().get(5));
			    String newAvg3 = Integer.toString(Math.round(newavg));
			    // Put this updated value in the map
			    ArrayList<String> a = entry.getValue();
			    a.set(1, newAvg2);
			    a.set(2, newAvg3);
			}
			System.out.println("The values have been rounded off");
			//For getting decimal places we need to store them in float store them in float
			System.out.println(String.format("%-12s %-12s %-12s %-15s %-15s", "CUSTOMER","PRODUCT","THE_AVG","OTHER_PROD_AVG","OTHER_CUST_AVG"));
			System.out.println(String.format("%-12s %-12s %-12s %-15s %-15s", "========","=======","=======","==============","=============="));
			
			for (Map.Entry<String,ArrayList<String>> entry:multiMap.entrySet()){
				String part = entry.getKey();
				String[] parts = part.split("\\+"); //Splits the key to get back cust and prod
	            String Cust = parts[0];
	            String prod = parts[1];
			

	            String key =entry.getKey();
	            ArrayList<String> value =entry.getValue();  
	            
	            System.out.println(String.format("%-12s %-12s %7s %19s %15s", Cust,prod,value.get(0),value.get(1),value.get(2)));
	            //break;
		}
		}

		catch(SQLException e)
		{
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}

	}

}


