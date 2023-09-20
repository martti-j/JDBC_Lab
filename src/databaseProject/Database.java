package databaseProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Database {
	static ResultSet rs;
	static Statement s;
	static PreparedStatement ps;
	static Connection con;
	static Scanner userInput = new Scanner(System.in);
	
	public static void main(String[] args) {
		try {
			Database pro = new Database();
			pro.createConnection();
			boolean status = true;
			while(status) {
				pro.getAllUsers();
				System.out.printf("Option 1 = Add user%n"
						+ "Option 2 = Update user information%n"
						+ "Option 3 = Delete user%n"
						+ "To exit wirte exit%n");
				
				System.out.println("Enter option number:"); 
			    String action = userInput.nextLine();
				
				switch(action) {
					case "1":
						pro.insertUser();
						break;
					case "2":
						pro.updateUser();
						break;
					case "3":
						pro.deleteUser();
						break;
					case "exit":
						status = false;
						break;					
					default:
						System.out.println("Incorrect input");
						break;
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException more info below.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLException more info below.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Something went wrong. More info is below.");
			e.printStackTrace();
	    } finally {
	    	try { rs.close(); } catch (Exception e) { /* Ignored */ }
	    	try { s.close(); } catch (Exception e) { /* Ignored */ }
	        try { ps.close(); } catch (Exception e) { /* Ignored */ }
	        try { con.close(); } catch (Exception e) { /* Ignored */ }
	        try { userInput.close(); } catch (Exception e) { /* Ignored */ }
	    }
	}

	void createConnection() throws ClassNotFoundException, SQLException, Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MYDB", "root", "PassWord12345!");			
		System.out.println("Database connection success");
	}
	
	void getAllUsers() throws SQLException, Exception {
		s = con.createStatement();
		rs = s.executeQuery("SELECT * FROM users");
		while(rs.next()) {
			int userID = rs.getInt("ID");
			String firstName = rs.getString("FirstName");
			String lastName = rs.getString("LastName");
			System.out.println(userID + " " + firstName + " " + lastName);
		}
	}
	
	void insertUser() throws SQLException, Exception {
		System.out.println("Enter firstname:"); 
	    String firstName = userInput.nextLine();
	    
	    System.out.println("Enter lastname:");
	    String lastName = userInput.nextLine();
    
    	ps = con.prepareStatement("INSERT INTO users (FirstName, LastName) VALUES (?, ?)");
    	ps.setString(1, firstName);
    	ps.setString(2, lastName);
    	ps.execute();
	}
	
	void updateUser() throws SQLException, Exception {		
		System.out.println("Enter user ID:"); 
	    String inputUserID = userInput.nextLine();
		
		System.out.println("Enter firstname:"); 
	    String inputFirstName = userInput.nextLine();
	    
	    System.out.println("Enter lastname:");
	    String inputLastName = userInput.nextLine();
	    
	    ps = con.prepareStatement("UPDATE users SET FirstName = ?, LastName = ? WHERE ID = ?");
    	ps.setString(1, inputFirstName);
    	ps.setString(2, inputLastName);
    	ps.setString(3, inputUserID);
    	ps.execute();
	}
	
	void deleteUser() throws SQLException, Exception {
		System.out.println("Enter user ID:"); 
	    String inputUserID = userInput.nextLine();
	    
	    ps = con.prepareStatement("DELETE FROM users WHERE ID = ?");
    	ps.setString(1, inputUserID);
    	ps.execute();
	}
}
