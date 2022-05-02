package classes;
import java.sql.*;

import DataBase.Queries;
public class Commentaire {
	private String refCom;
	private String Com ;
	
	public Commentaire(String refCom,String Com){
		this.refCom=refCom;
		this.Com=Com;
	}

	public String getRefCom() {
		return refCom;
	}

	public void setRefCom(String refCom) {
		this.refCom = refCom;
	}

	public String getCom() {
		return Com;
	}

	public void setCom(String com) {
		Com = com;
	}



	

	 public static void main(String[] args)throws SQLException {
	Connection connect = Queries.conn();
	ResultSet rs = connect.createStatement().executeQuery("SELECT * FROM Commentaire ");
	while(rs.next())
	{
	System.out.println(rs.getString("name"));
	}
	}

	
	
}
