package dto;

import java.time.LocalDate;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class BookRoom {
	private SimpleStringProperty bookRoomId, customerId, staffId;
	private SimpleObjectProperty<LocalDate> startDay;
	
	public BookRoom(String bookRoomId, String customerId, String staffId, LocalDate startDay) {
		super();
		this.bookRoomId = new SimpleStringProperty(bookRoomId);
		this.customerId = new SimpleStringProperty(customerId);
		this.staffId = new SimpleStringProperty(staffId);
		this.startDay = new SimpleObjectProperty<LocalDate>(startDay);
	}
	
	

	public BookRoom(BookRoom value) {
		this(value.getBookRoomId().get(), value.getCustomerId().get(), value.getStaffId().get(), value.getStartDay().get());
	}
	
	public BookRoom() {
		this(null,null,null,null);
	}

	public SimpleStringProperty getBookRoomId() {
		return bookRoomId;
	}

	public SimpleStringProperty getCustomerId() {
		return customerId;
	}

	public SimpleStringProperty getStaffId() {
		return staffId;
	}

	public SimpleObjectProperty<LocalDate> getStartDay() {
		return startDay;
	}

	public void setBookRoomId(String bookRoomId) {
		this.bookRoomId.set(bookRoomId);;
	}

	public void setCustomerId(String customerId) {
		this.customerId.set(customerId);;
	}

	public void setStaffId(String staffId) {
		this.staffId.set(staffId);;
	}

	public void setStartDay(LocalDate startDay) {
		this.startDay.set(startDay);
	}
	
	//TODO: to add new customer to database
	public static void addCustomer(Customer customer) {
		
	}
	
	//TODO: to search book room card
	public static BookRoom searchOrder(ObservableList<BookRoom> bookRooms, String bookRoomID){
		BookRoom bookRoom = new BookRoom();
		String temp = bookRoomID.trim();
		if("".equals(temp))
			return null;
		for(BookRoom book : bookRooms) {
			if(temp.equalsIgnoreCase(book.getBookRoomId().get()))
				bookRoom = book;
		}
		return bookRoom;
	}
}
