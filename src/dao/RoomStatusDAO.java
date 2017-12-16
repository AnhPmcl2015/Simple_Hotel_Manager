package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbconnection.DBConnection;
import dto.RoomStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoomStatusDAO {
	public static ObservableList<RoomStatus> listRoomStatus;
	public static ObservableList<String> listRoomStatusName;
	
	public static void getAllRoomStatus() throws SQLException {
		if(listRoomStatus == null) {
			listRoomStatus = FXCollections.observableArrayList();
		}else {
			listRoomStatus.clear();
		}
		
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		Statement statement = con.createStatement();
		String sql = "select * from RoomStatus";
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()) {
			int id = rs.getInt(1);
			String name = rs.getString(2);
			boolean deleted = rs.getBoolean(3);
			
			listRoomStatus.add(new RoomStatus(id, name, deleted));
		}
		con.close();
		
		if(listRoomStatusName == null) {
			listRoomStatusName = FXCollections.observableArrayList();
		}else {
			listRoomStatusName.clear();
		}
		
		for(RoomStatus r : listRoomStatus) {
			listRoomStatusName.add(r.getRoomStatusName().get());
		}
	}
}
