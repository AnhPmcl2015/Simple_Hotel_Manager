package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbconnection.DBConnection;
import dto.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public final class RoomDAO {
	public static ObservableList<Room> listRoom;
	public static ObservableList<Room> listRoomWithoutDeleted;
	
	public static void getAllRoom() throws SQLException {
		if(listRoom == null) {
			listRoom = FXCollections.observableArrayList();
		}else {
			listRoom.clear();
		}
		
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		Statement statement = con.createStatement();
		String sql = "select * from Room where deletedRoom = 0";
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()) {
			String roomId = rs.getString(1);
			String roomName = rs.getString(2);
			String roomNote = rs.getString(3);
			boolean deletedRoom = rs.getBoolean(4);
			int roomStatusId = rs.getInt(5);
			int roomTypeId = rs.getInt(6);
			
			listRoom.add(new Room(roomId, roomName, roomNote, deletedRoom, roomTypeId, roomStatusId));
		}
		
		con.close();
	}
	
	public static void getAllRoomWithoutDeleted() throws SQLException {
		if(listRoomWithoutDeleted == null) {
			listRoomWithoutDeleted = FXCollections.observableArrayList();
		}else {
			listRoomWithoutDeleted.clear();
		}
		
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		Statement statement = con.createStatement();
		String sql = "select * from Room";
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()) {
			String roomId = rs.getString(1);
			String roomName = rs.getString(2);
			String roomNote = rs.getString(3);
			boolean deletedRoom = rs.getBoolean(4);
			int roomStatusId = rs.getInt(5);
			int roomTypeId = rs.getInt(6);
			
			listRoomWithoutDeleted.add(new Room(roomId, roomName, roomNote, deletedRoom, roomTypeId, roomStatusId));
		}
		
		con.close();

	}

	public static void deleteRoom(String roomId, String roomName) throws SQLException {
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		
		String sql = "update room set deletedRoom = 1 where roomId = ?";
		
		
		PreparedStatement pr = con.prepareStatement(sql);
		
		pr.setString(1, roomId);
		
		int check = pr.executeUpdate();
		if(check!=0) {
			Alert dialog = new Alert(AlertType.INFORMATION);
			dialog.setTitle("Delete room");
			dialog.setHeaderText("Room " + roomName + " was deleted!!!!");
			dialog.showAndWait();
		}
		con.close();
		
		getAllRoom();
		getAllRoomWithoutDeleted();
	}
	
	public static void updateRoomInfo(Room room) throws SQLException {
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		
		String sql = "update room set roomName = ?, roomNote = ?, roomStatusId = ?, roomTypeId = ? where roomId = ?";
		
		
		PreparedStatement pr = con.prepareStatement(sql);
		
		pr.setString(1, room.getRoomName().get());
		pr.setString(2, room.getRoomNote().get());
		pr.setInt(3, room.getRoomStatusId().get());
		pr.setInt(4, room.getRoomTypeId().get());
		pr.setString(5, room.getRoomId().get());
		
		int check = pr.executeUpdate();
		if(check!=0) {
			Alert dialog = new Alert(AlertType.INFORMATION);
			dialog.setTitle("Updating room");
			dialog.setHeaderText("Room " + room.getRoomName().get() + " was updated!!!!");
			dialog.showAndWait();
		}
		con.close();
		
		getAllRoom();
		getAllRoomWithoutDeleted();
	}

	public static void addNewRoom(Room room) throws SQLException {
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		
		String sql = "insert into room values(?,?,?,?,?,?)";
		
		
		PreparedStatement pr = con.prepareStatement(sql);
		
		pr.setString(1, room.getRoomId().get());
		pr.setString(2, room.getRoomName().get());
		pr.setString(3, room.getRoomNote().get());

		pr.setBoolean(4, room.getDeletedRoom().get());
		pr.setInt(5, room.getRoomStatusId().get());
		pr.setInt(6, room.getRoomTypeId().get());
		
		int check = pr.executeUpdate();
		if(check!=0) {
			Alert dialog = new Alert(AlertType.INFORMATION);
			dialog.setTitle("Adding room");
			dialog.setHeaderText("Room " + room.getRoomName().get() + " was added!!!!");
			dialog.showAndWait();
		}
		con.close();
		
		getAllRoom();
		getAllRoomWithoutDeleted();
	}
	
	public static String countRoomPerType(int index) {
		String kq = "";
		int count = 0;
		if(index == 1) 
			kq += "A_1";
		else if(index == 2) {
			kq += "B_2";
		}
		else if(index==3) {
			kq+="C_3";
		}
		
		for(Room r : listRoomWithoutDeleted) {
			if(r.getRoomTypeId().get() == index) {
				count ++;
			}
		}
		
		count ++;
		String temp = count +"";
		if(temp.length()<2) {
			kq+="0";
		}
		kq += temp;
		return kq;
	}
	
	public static void updateRoomToRented(ObservableList<Room> list) throws SQLException {
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		

		for(Room r : list) {
			String sql = "update room set roomStatusId = 2 where roomName = ?";
			PreparedStatement pr = con.prepareStatement(sql);
			
			pr.setString(1, r.getRoomName().get());
			pr.executeUpdate();
		}
		con.close();
		
		getAllRoom();
		getAllRoomWithoutDeleted();
	}
}
