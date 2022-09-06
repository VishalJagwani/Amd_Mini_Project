package com.bank.amd;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Scanner;


public class bank {
	
	String fname, lname;
	double balance;
	int accNo;
	BufferedReader br;
	
	bank(){
		br = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public int openAcc() throws IOException {

		System.out.print("\n\t Please enter your first name: ");
		fname = br.readLine();
		
		System.out.print("\n\t Please enter your last name: ");
		lname = br.readLine();
		
		System.out.print("\n\t Please enter your opening balance: ");
		balance = Double.parseDouble(br.readLine());
		
		accNo = (int)((Math.random()*100)+1);

		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection cnn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl.iiht.tech", "scott", "tiger");
			
			PreparedStatement stmt = cnn.prepareStatement("insert into bank values(?,?,?,?)");
			stmt.setInt(1, accNo);
			stmt.setString(2, fname);
			stmt.setString(3, lname);
			stmt.setDouble(4, balance);
			
			int i = stmt.executeUpdate();

		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Exception occured");
		}

		return accNo;
	}
	
	public void checkBalance() throws IOException{
		
		int accNo1;

		System.out.print("\n\t Please enter your account number: ");
		accNo1 = Integer.parseInt(br.readLine());
		cRecord(accNo1);

		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection cnn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl.iiht.tech", "scott", "tiger");
			
			PreparedStatement stmt = cnn.prepareStatement("select balance from bank where accNo = ?");
			stmt.setInt(1, accNo1);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				System.out.println("\n\t Your balance is: "+rs.getString(1));
			} 
			}
			catch(Exception e) {
				e.printStackTrace();
				System.out.println("Exception occured");
			}
		}
	
	public void withdraw() throws IOException {
		
		int accNo1;
		double bal,ubal;
		int amt;

		System.out.print("\n\t Please enter your account number: ");
		accNo1 = Integer.parseInt(br.readLine());
		cRecord(accNo1);
		
		System.out.print("\n\t Please enter the amount to withdraw: ");
		amt = Integer.parseInt(br.readLine());
			
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection cnn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl.iiht.tech", "scott", "tiger");
			
			PreparedStatement stmt = cnn.prepareStatement("select balance from bank where accNo = ?");
			stmt.setInt(1, accNo1);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				bal = rs.getDouble(1);
				if(amt>bal) {
					System.out.println("\n\t Insufficient balance");
					System.out.println("\n\t Your balance is "+bal);
				}
				else if(amt<=0) {
					System.out.println("\n\t Please enter a valid amount");
				}
				else {
					ubal = bal - amt;
					PreparedStatement ps = cnn.prepareStatement("update bank set balance = ? where accNo = ?");
					ps.setDouble(1, ubal);
					ps.setInt(2, accNo1);
					
					int i = ps.executeUpdate(); 
					System.out.println("\n\t Amount withdrawn: "+amt);
					System.out.println("\n\t Your balance is: "+ubal);
				}
			}
	
			}
			catch(Exception e) {
				e.printStackTrace();
				System.out.println("Exception occured");
			}
	}
	
	public void deposit() throws IOException {
		
		int accNo1;
		double bal,ubal;
		int amt;
		
		System.out.print("\n\t Please enter your account number: ");
		accNo1 = Integer.parseInt(br.readLine());
		cRecord(accNo1);

		System.out.print("\n\t Please enter the amount to deposit: ");
		amt = Integer.parseInt(br.readLine());
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection cnn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl.iiht.tech", "scott", "tiger");
			
			PreparedStatement stmt = cnn.prepareStatement("select balance from bank where accNo = ?");
			stmt.setInt(1, accNo1);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				bal = rs.getDouble(1);
				if(amt<=0) {
					System.out.println("\n\t Please enter a valid amount");
				}
				else {
					ubal = bal + amt;
					PreparedStatement ps = cnn.prepareStatement("update bank set balance = ? where accNo = ?");
					ps.setDouble(1, ubal);
					ps.setInt(2, accNo1);
					
					int i = ps.executeUpdate(); 
					System.out.println("\n\t Amount deposited: "+amt);
					System.out.println("\n\t Your balance is: "+ubal);
				}
			}
		
			}
			catch(Exception e) {
				e.printStackTrace();
				System.out.println("Exception occured");
			}
		
	}
	
	public void cRecord(int accNo1) {
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection cnn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl.iiht.tech", "scott", "tiger");
			
			CallableStatement stmt = cnn.prepareCall("{? = call chckRec(?)}");
			
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, accNo1);
			stmt.execute();
			int x = stmt.getInt(1);
		
			if(x == 1) {
				System.out.println("\n\t Welcome!");
			}
			else {
				System.out.println("\n\t Account does not exists, Please enter valid account number.");
			}
			
			}
			catch(Exception e) {
				System.out.println("\n\t Exception occured, Please enter valid account number");
				
			}
	}
		
	
	public static void main(String[] args) throws IOException{
		
		int ch;
		bank obj = new bank();
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("\n\t MENU");
			System.out.println("\n\t 1. Open new account");
			System.out.println("\n\t 2. Check balance");
			System.out.println("\n\t 3. Withdraw money");
			System.out.println("\n\t 4. Deposit money");
			System.out.println("\n\t 5. Exit");
			
			System.out.print("\n\t Enter your choice: ");
			ch = sc.nextInt();
			
			switch(ch) {
				case 1:
					//open account
					int accNo;
					accNo = obj.openAcc();
					System.out.println("\n\t Your account number is: "+accNo);
					System.out.println("\n\t Account opened successfully");
					break;
				case 2:
					//check balance
					obj.checkBalance();
					break;
				case 3:
					//withdraw
					obj.withdraw();
					break;
				case 4:
					//deposit
					obj.deposit();
					break;
				case 5:
					System.out.println("\n\t Thank you. Have a nice day!");
					break;
				default:
					System.out.println("\n\t Invalid choice.");
					break;
			}
			
		} while(ch!=5);
		sc.close();
		
	}
}
