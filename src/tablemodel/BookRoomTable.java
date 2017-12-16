package tablemodel;

import javafx.beans.property.SimpleStringProperty;

public class BookRoomTable {
	private SimpleStringProperty roomName;
	private SimpleStringProperty numberOfGuests;
	
	public BookRoomTable(String roomName, String numberOfGuests) {
		super();
		this.roomName = new SimpleStringProperty(roomName);
		this.numberOfGuests = new SimpleStringProperty(numberOfGuests);
	}

	public BookRoomTable() {
		this(null,null);
	}

	public BookRoomTable(BookRoomTable newValue) {
		this(newValue.getRoomName().get(), newValue.getNumberOfGuests().get());
	}
	
	public SimpleStringProperty getRoomName() {
		return roomName;
	}

	public SimpleStringProperty getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setRoomName(String roomName) {
		this.roomName.set(roomName);
	}

	public void setNumberOfGuests(String numberOfGuests) {
		this.numberOfGuests.set(numberOfGuests);
	}
	
	
}
