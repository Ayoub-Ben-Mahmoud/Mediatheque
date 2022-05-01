package DataBase;
import java.sql.*;
public class Queries {
	public static Connection conn() throws SQLException{
  Connection conn = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
  return conn;
	}}

