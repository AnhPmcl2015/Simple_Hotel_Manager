package dto;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RoomStatus {
	private SimpleIntegerProperty roomStatusId;
	private SimpleStringProperty roomStatusName;
	private SimpleBooleanProperty availableRoom;
	
	public RoomStatus(int roomStatusId, String roomStatusName,
			boolean availableRoom) {
		super();
		this.roomStatusId = new SimpleIntegerProperty(roomStatusId);
		this.roomStatusName = new SimpleStringProperty(roomStatusName);
		this.availableRoom = new SimpleBooleanProperty(availableRoom);
	}
	
	public RoomStatus() {
		this(1,null,true);
	}

	public SimpleIntegerProperty getRoomStatusId() {
		return roomStatusId;
	}

	public SimpleStringProperty getRoomStatusName() {
		return roomStatusName;
	}

	public SimpleBooleanProperty getAvailableRoom() {
		return availableRoom;
	}
	
}
