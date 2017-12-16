package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbconnection.DBConnection;
import dto.Staff;

public final class TemplateDAO {
	
	public static float surcharge = 0;
	public static int maximumCustomers = 0;
	public static Staff currentStaff;
	
	public static void getTemplate() throws SQLException {
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		Statement statement = con.createStatement();
		String sql = "select * from Template";
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()) {
			float sur = rs.getFloat(1);
			int maximum = rs.getInt(2);
			
			surcharge = sur;
			maximumCustomers = maximum;
		}
		
		con.close();
	}
	
	public static void changeSurcharge(float newValue) throws SQLException {
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		
		String sql = "update Template set surcharge = ? where maximumGuests = ?";
		
		
		PreparedStatement pr = con.prepareStatement(sql);
		
		pr.setFloat(1, newValue);
		pr.setInt(2, maximumCustomers);
		pr.executeUpdate();
		con.close();
		
		getTemplate();
	}
	
	public static void changeMaximumGuests(int newValue) throws SQLException {
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		
		String sql = "update Template set maximumGuests = ? where surcharge = ?";
		
		
		PreparedStatement pr = con.prepareStatement(sql);
		
		pr.setInt(1, newValue);
		pr.setFloat(2, surcharge);
		
		pr.executeUpdate();
		con.close();
		
		getTemplate();
	}
	
}
