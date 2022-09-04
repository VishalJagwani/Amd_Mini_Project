package com.bank.amd;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dbConnection {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
		try {
		System.out.println("\n\t Connection initiated");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		String url = "jdbc:oracle:thin:@localhost:1521/orcl.iiht.tech";
		String user = "scott";
		String pwd = "tiger";
		
		Connection cnn = DriverManager.getConnection(url, user, pwd);
		
		if (cnn != null) {
			System.out.println("\n\t Connected successfully");
		}
		else 
			System.out.println("\n\t Connection failed");
		
		Statement stmt = cnn.createStatement();
		
		ResultSet rs = stmt.executeQuery("select * from bank");
		
		while(rs.next()) {
			System.out.println("\n\t Account Number: "+rs.getInt(1)+"  First name: "+rs.getString(2)+"  Last Name: "+rs.getString(3)+"  Balance: "+rs.getDouble(4));
		} 
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Exception occured");
		}
		
	}

}