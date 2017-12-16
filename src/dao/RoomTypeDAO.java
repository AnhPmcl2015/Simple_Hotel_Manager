package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbconnection.DBConnection;
import dto.RoomType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RoomTypeDAO {
	public static ObservableList<RoomType> listRoomType, listAllRoomType;
	public static ObservableList<String> listRoomTypeName;
	
	public static void getAllRoomType() throws SQLException {
		if(listRoomType == null) {
			listRoomType = FXCollections.observableArrayList();
		}else {
			listRoomType.clear();
		}
		
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		Statement statement = con.createStatement();
		String sql = "select * from RoomType where deletedRoomType = 0";
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()) {
			int roomTypeId = rs.getInt(1);
			String roomTypeName = rs.getString(2);
			long price = rs.getLong(3);
			boolean deletedRoomType = rs.getBoolean(4);
			
			listRoomType.add(new RoomType(roomTypeId, roomTypeName, deletedRoomType, price));
		}
		
		con.close();
		
		if(listRoomTypeName == null) {
			listRoomTypeName = FXCollections.observableArrayList();
		}else {
			listRoomTypeName.clear();
		}
		
		listRoomTypeName.add("All");
		
		for(RoomType i : listRoomType) {
			listRoomTypeName.add(i.getRoomNameId().get() + " - " + i.getRoomTypePrice().get()+"VND");
		}
		
	}
	
	public static void getAllRoomTypeWithoutDeleted() throws SQLException {
		if(listAllRoomType == null) {
			listAllRoomType = FXCollections.observableArrayList();
		}else {
			listAllRoomType.clear();
		}
		
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		Statement statement = con.createStatement();
		String sql = "select * from RoomType";
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()) {
			int roomTypeId = rs.getInt(1);
			String roomTypeName = rs.getString(2);
			long price = rs.getLong(3);
			boolean deletedRoomType = rs.getBoolean(4);
			
			listAllRoomType.add(new RoomType(roomTypeId, roomTypeName, deletedRoomType, price));
		}
		
		con.close();
	}
	
	public static void updateRoomTypeInfo(RoomType roomType) throws SQLException {
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		
		String sql = "update RoomType set roomTypeName = ?, price =?, deletedRoomType = 0 where roomTypeId = ?";
		
		
		PreparedStatement pr = con.prepareStatement(sql);
		
		pr.setString(1, roomType.getRoomNameId().get());
		pr.setLong(2, roomType.getRoomTypePrice().get());
		pr.setInt(3, roomType.getRoomTypeId().get());
		
		int check = pr.executeUpdate();
		if(check!=0) {
			Alert dialog = new Alert(AlertType.INFORMATION);
			dialog.setTitle("Updating room's type");
			dialog.setHeaderText("Room's type " + roomType.getRoomNameId().get() + " was updated!!!!");
			dialog.showAndWait();
		}
		con.close();
		
		getAllRoomType();
		getAllRoomTypeWithoutDeleted();
	}
	
	public static void deleteRoomType(int roomTypeId, String roomTypeName) throws SQLException {
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		
		String sql = "update RoomType set deletedRoomType = 1 where roomTypeId = ?";
		
		
		PreparedStatement pr = con.prepareStatement(sql);
		
		pr.setInt(1, roomTypeId);
		
		int check = pr.executeUpdate();
		if(check!=0) {
			Alert dialog = new Alert(AlertType.INFORMATION);
			dialog.setTitle("Updating room's type");
			dialog.setHeaderText("Room's type " + roomTypeName + " was deleted!!!!");
			dialog.showAndWait();
		}
		con.close();
		
		getAllRoomType();
		getAllRoomTypeWithoutDeleted();
	}
	
	public static void addNewRoomType(RoomType roomType) throws SQLException {
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		
		String sql = "insert into room values(?,?,?,?)";
		
		
		PreparedStatement pr = con.prepareStatement(sql);
		
		pr.setInt(1, roomType.getRoomTypeId().get());
		pr.setString(2, roomType.getRoomNameId().get());
		pr.setLong(3, roomType.getRoomTypePrice().get());
		pr.setBoolean(4, roomType.getDeletedRoomType().get());
		
		int check = pr.executeUpdate();
		if(check!=0) {
			Alert dialog = new Alert(AlertType.INFORMATION);
			dialog.setTitle("Adding new room's type");
			dialog.setHeaderText("Room's type " + roomType.getRoomNameId().get() + " was added!!!!");
			dialog.showAndWait();
		}
		con.close();
		
		getAllRoomType();
		getAllRoomTypeWithoutDeleted();
	}
}
