package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbconnection.DBConnection;
import dto.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class StaffDAO {
	
	public static ObservableList<Staff> listStaff;
	
	public static void getAllStaff() throws SQLException {
		
		if(listStaff == null) {
			listStaff = FXCollections.observableArrayList();
		}else {
			listStaff.clear();
		}
		
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		Statement statement = con.createStatement();
		String sql = "select * from Staff";
		ResultSet rs = statement.executeQuery(sql);
		Staff staff;
		while(rs.next()) {
			String staffId = rs.getString(1);
			String staffName = rs.getString(2);
			String staffAddress = rs.getString(3);
			String governmentId = rs.getString(4);
			String username = rs.getString(5);
			String password = rs.getString(6);
			boolean isManager = rs.getBoolean(7);
			
			staff = new Staff(staffName, staffAddress, governmentId, staffId, username, isManager, password) {};
			listStaff.add(staff);
		}
		con.close();
	}
	
	public static void updateStaffInfo(Staff staff) throws SQLException {
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		
		String sql = "update staff set staffName = ?, staffAddress = ?, governmentId = ?, username = ?, staffPassword = ?, isManager = ? where staffId = ?";
		
		
		PreparedStatement pr = con.prepareStatement(sql);
		
		
		pr.setString(1, staff.getPeopleName().get());
		pr.setString(2, staff.getPeopleAddress().get());
		pr.setString(3, staff.getGovernmentId().get());
		pr.setString(4, staff.getUsername().get());
		pr.setString(5, staff.getPassword().get());
		pr.setBoolean(6, staff.getIsManager().get());
		pr.setString(7, staff.getStaffId().get());
		
		int check = pr.executeUpdate();
		if(check!=0) {
			Alert dialog = new Alert(AlertType.INFORMATION);
			dialog.setTitle("Updating room");
			dialog.setHeaderText("Staff " + staff.getPeopleName().get() + " was updated!!!!");
			dialog.showAndWait();
		}
		con.close();
		
		getAllStaff();
	}
	
	public static void addNewStaf(Staff staff) throws SQLException {
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		
		String sql = "insert into staff values(?,?,?,?,?,?,?)";
		
		
		PreparedStatement pr = con.prepareStatement(sql);
		
		pr.setString(1, staff.getStaffId().get());
		pr.setString(2, staff.getPeopleName().get());
		pr.setString(3, staff.getPeopleAddress().get());
		pr.setString(4, staff.getGovernmentId().get());
		pr.setString(5, staff.getUsername().get());
		pr.setString(6, staff.getPassword().get());
		pr.setBoolean(7, staff.getIsManager().get());

		
		int check = pr.executeUpdate();
		if(check!=0) {
			Alert dialog = new Alert(AlertType.INFORMATION);
			dialog.setTitle("Adding room");
			dialog.setHeaderText("Staff " + staff.getPeopleName().get() + " was added!!!!");
			dialog.showAndWait();
		}
		con.close();
		
		getAllStaff();
	}
}
