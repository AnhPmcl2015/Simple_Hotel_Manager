package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dbconnection.DBConnection;
import dto.BookRoom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class BookRoomDAO {
	public static ObservableList<BookRoom> listBookRoom;
	
	public static void getAllBookRoom() throws SQLException {
		
		if(listBookRoom == null) {
			listBookRoom = FXCollections.observableArrayList();
		}else {
			listBookRoom.clear();
		}
		
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		Statement statement = con.createStatement();
		String sql = "select * from BookRoom";
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()) {
			
			String bookRoomId = rs.getString(1);
			String customerId = rs.getString(2);
			String staffId = rs.getString(3);
			int day = rs.getInt(4);
			int month = rs.getInt(5);
			int year = rs.getInt(6);
			listBookRoom.add(new BookRoom(bookRoomId, customerId, staffId, LocalDate.of(year, month, day)));
		}
		
		con.close();
	}
	
	public static void addNewBookRoom(BookRoom bookRoom) throws SQLException {
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		
		String sql = "insert into bookroom values(?,?,?,?,?,?)";
		
		PreparedStatement pr = con.prepareStatement(sql);

		pr.setString(1, bookRoom.getBookRoomId().get());
		pr.setString(2, bookRoom.getCustomerId().get());
		pr.setString(3, bookRoom.getStaffId().get());
		
		int day = Integer.parseInt(bookRoom.getStartDay().get().format(DateTimeFormatter.ofPattern("dd")));
		int month = Integer.parseInt(bookRoom.getStartDay().get().format(DateTimeFormatter.ofPattern("MM")));
		int year = Integer.parseInt(bookRoom.getStartDay().get().format(DateTimeFormatter.ofPattern("yyyy")));
		
		pr.setInt(4, day);
		pr.setInt(5, month);
		pr.setInt(6, year);
		
		
		int check = pr.executeUpdate();
		if(check!=0) {
			Alert dialog = new Alert(AlertType.INFORMATION);
			dialog.setTitle("Booking room");
			dialog.setHeaderText("Success!!!");
			dialog.showAndWait();
		}
		con.close();
		
		getAllBookRoom();
	}
}
