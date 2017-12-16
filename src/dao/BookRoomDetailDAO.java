package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbconnection.DBConnection;
import dto.BookRoomDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookRoomDetailDAO {
	public static ObservableList<BookRoomDetail> listBookRoomDetail;
	
	public static void getAllBookRoomDetail() throws SQLException {
		
		if(listBookRoomDetail == null) {
			listBookRoomDetail = FXCollections.observableArrayList();
		}else {
			listBookRoomDetail.clear();
		}
		
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		Statement statement = con.createStatement();
		String sql = "select * from BookRoomDetail";
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()) {
			String bookRoomDetailId = rs.getString(1);
			String roomId = rs.getString(2);
			String bookRoomId = rs.getString(3);
			int numberOfGuests = rs.getInt(4);
			listBookRoomDetail.add(new BookRoomDetail(bookRoomDetailId, roomId, bookRoomId,numberOfGuests));
		}
		
		con.close();
	}
	
	public static void addNewBookRoomDetail(ObservableList<BookRoomDetail> list) throws SQLException {
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		

		for(BookRoomDetail r : list) {
			String sql = "insert into bookroomdetail values(?,?,?,?)";
			PreparedStatement pr = con.prepareStatement(sql);
			
			pr.setString(1, r.getBookRoomDetailId().get());
			pr.setString(2, r.getRoomId().get());
			pr.setString(3, r.getBookRoomID().get());
			pr.setInt(4, r.getNumberOfGuests().get());
			pr.executeUpdate();
		}
		con.close();
		
		getAllBookRoomDetail();
	}
	
}
