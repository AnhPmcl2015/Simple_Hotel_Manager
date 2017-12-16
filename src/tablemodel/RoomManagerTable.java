package tablemodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RoomManagerTable {
	private SimpleStringProperty roomId;
	private SimpleStringProperty roomName;
	private SimpleStringProperty roomType;
	private SimpleStringProperty price;
	private SimpleStringProperty note;
	private SimpleStringProperty status;
	private SimpleIntegerProperty roomTypeId;
	private SimpleIntegerProperty roomStatusId;
	
	public RoomManagerTable(String roomId, String roomName,
			String roomType, String price, String note,
			String status, int roomTypeId, int roomStatusId) {
		super();
		this.roomId = new SimpleStringProperty(roomId);
		this.roomName = new SimpleStringProperty(roomName);
		this.roomType = new SimpleStringProperty(roomType);
		this.price = new SimpleStringProperty(price);
		this.note = new SimpleStringProperty(note);
		this.status = new SimpleStringProperty(status);
		this.roomTypeId = new SimpleIntegerProperty(roomTypeId);
		this.roomStatusId = new SimpleIntegerProperty(roomStatusId);
	}
	
	public RoomManagerTable() {
		this(null,null,null,null,null,null,1,1);
	}

	public RoomManagerTable(RoomManagerTable newValue) {
		this(newValue.getRoomId().get(),newValue.getRoomName().get(),newValue.getRoomType().get(), newValue.getPrice().get(), newValue.getNote().get(),newValue.getStatus().get(),
				newValue.getRoomTypeId().get(), newValue.getRoomStatusId().get());
	}
	
	public SimpleStringProperty getRoomId() {
		return roomId;
	}

	public SimpleStringProperty getRoomName() {
		return roomName;
	}

	public SimpleStringProperty getRoomType() {
		return roomType;
	}

	public SimpleStringProperty getPrice() {
		return price;
	}

	public SimpleStringProperty getNote() {
		return note;
	}

	public SimpleStringProperty getStatus() {
		return status;
	}
	
	public SimpleIntegerProperty getRoomTypeId() {
		return roomTypeId;
	}
	
	public SimpleIntegerProperty getRoomStatusId() {
		return roomStatusId;
	}
	//--------------------------
	
	public void setRoomId(String roomId) {
		this.roomId.set(roomId);
	}

	public void setRoomName(String roomName) {
		this.roomName.set(roomName);
	}

	public void setRoomType(String roomType) {
		this.roomType.set(roomType);
	}

	public void setPrice(String price) {
		this.price.set(price);
	}

	public void setNote(String note) {
		this.note.set(note);
	}

	public void setStatus(String status) {
		this.status.set(status);
	}
	
	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId.set(roomTypeId);
	}
	
	public void setRoomStatusId(int roomStatusId) {
		this.roomStatusId.set(roomStatusId);
	}
}
