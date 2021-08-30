package pac1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

public class InsertARowValueTest {
	public static void main(String[] args) throws Throwable {
		Connection connection=null;
		try {
			//register the connection
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
			
			//get the connection
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root");
			//issue the statement
			Statement statement = connection.createStatement();
			//update the query
			int result = statement.executeUpdate("insert into student_info values('8','chin','aa','aa')");
			
			if(result==1) {
				System.out.println("Data is added to the database");
			} else {
				System.out.println("Data is not added to the database");
			}
		} catch (Exception e) {
			
		} finally {
			//close the connection
			connection.close();
		}
	}

}
