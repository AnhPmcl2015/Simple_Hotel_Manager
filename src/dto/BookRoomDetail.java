package dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BookRoomDetail {
	private SimpleStringProperty bookRoomDetailId, roomId, bookRoomID;
	private SimpleIntegerProperty numberOfGuests;

	public BookRoomDetail(String bookRoomDetailId, String roomId, String bookRoomId, int numberOfGuests) {
		super();
		this.bookRoomDetailId = new SimpleStringProperty(bookRoomDetailId);
		this.roomId = new SimpleStringProperty(roomId);
		this.bookRoomID = new SimpleStringProperty(bookRoomId);
		this.numberOfGuests = new SimpleIntegerProperty(numberOfGuests);
	}
	
	public BookRoomDetail(BookRoomDetail newValue) {
		this(newValue.getBookRoomDetailId().get(), newValue.getRoomId().get(), newValue.getBookRoomID().get(), newValue.getNumberOfGuests().get());
	}

	public BookRoomDetail() {
		this(null,null,null, 1);
	}

	public SimpleStringProperty getBookRoomDetailId() {
		return bookRoomDetailId;
	}

	public SimpleStringProperty getRoomId() {
		return roomId;
	}

	public SimpleStringProperty getBookRoomID() {
		return bookRoomID;
	}
	
	public SimpleIntegerProperty getNumberOfGuests() {
		return numberOfGuests;
	}


	public void setBookRoomDetailId(String bookRoomDetailId) {
		this.bookRoomDetailId.set(bookRoomDetailId);;
	}

	public void setRoomId(String roomId) {
		this.roomId.set(roomId);;
	}

	public void setBookRoomID(String bookRoomID) {
		this.bookRoomID.set(bookRoomID);;
	}
	
	public void setNumberOfGuests(int value) {
		this.numberOfGuests.set(value);
	}
	
}
