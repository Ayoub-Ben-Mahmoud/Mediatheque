package DataBase;
import java.sql.*;
public class Queries {
	
	
	public static Connection conn(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
	        //System.out.println("Loaded driver");
	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Mediatheque?user=root");
	        //System.out.println("Connected to MySQL");
	        return con;
	 } 
	 catch (Exception ex) {
	        ex.printStackTrace();
	 }
		return null;
	}}

