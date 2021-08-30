package practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

public class countColumnAndRowsOfTable {

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
			ResultSet result = statement.executeQuery("select * from student_info");
			
			//count columns
			ResultSetMetaData mData = result.getMetaData();
			System.out.println("No.of columns = "+mData.getColumnCount());
			
			//count rows
			int count=0;
			while(result.next()) {
				count++;
			}
			System.out.println("No.of rows = "+count);
			
		} catch (Exception e) {
			
		} finally {
			//close the connection
			connection.close();
		}
		
	}
}
