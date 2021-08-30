package pac1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.Driver;

public class GetTheDataFromTheDataBaseTest {
	public static void main(String[] args) throws Throwable {
		
		//step 1: register the connection
		Driver driver = new Driver();
		DriverManager.registerDriver(driver);
		
		//step 2: establish the connection
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root");
		
		//step 3:issue the statement
		Statement statement = connection.createStatement();
		
		//step 4:execute the query
		ResultSet result = statement.executeQuery("select * from student_info");
		
//		while(result.next()) {
//			System.out.println(result.getString(1)+"\t"+result.getString(2)+"\t"+result.getString(3)+"\t"+result.getString(4));
//		}
		List<String> firstNameList = new ArrayList<String>();
		
		while(result.next()) {
			String[] arr = result.getString(2).split("\n");
			for(String str :arr) {
				firstNameList.add(str);
			}
		}
		
		System.out.println(firstNameList);
		
		//System.out.println(firstNameList);
		//step 5: close the connection
		connection.close();
		
		
		
		
	}
	
}
