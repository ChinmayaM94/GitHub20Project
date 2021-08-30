package practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

public class GetDataFromDatabaseTest {

	public static void main(String[] args) throws Throwable {
		
		Connection connection=null;
		try {
			//register the driver
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
			
			//establish the connection
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root");
			
			//issue the statement
			Statement statement = connection.createStatement();
			
			//execute the query
			ResultSet result = statement.executeQuery("select firstname from student_info");
			
			//verifying the particular firstname present in table
			boolean isNamePresent = false;
			while(result.next()) {
				if(result.getString("firstname").equals("smith")) {
					isNamePresent = true;
					break;
				}
			}
			
			if(isNamePresent) {
				System.out.println("PASS:: firstname is present in table");
			} else {
				System.out.println("FAIL:: firstname is not present in table");
			}	
			
		} catch (Exception e) {
			
		} finally {
			//close the connection
			connection.close();
		}
		
	}
}
