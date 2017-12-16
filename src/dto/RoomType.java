package dto;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class RoomType {
	private SimpleIntegerProperty roomTypeId;
	private SimpleStringProperty roomTypeName;
	private SimpleBooleanProperty deletedRoomType;
	private SimpleLongProperty roomTypePrice;
	
	public RoomType(int roomTypeId, String roomTypeName,
			boolean deletedRoomType, long roomTypePrice) {
		super();
		this.roomTypeId = new SimpleIntegerProperty(roomTypeId);
		this.roomTypeName = new SimpleStringProperty(roomTypeName);
		this.deletedRoomType = new SimpleBooleanProperty(deletedRoomType);
		this.roomTypePrice = new SimpleLongProperty(roomTypePrice);
	}

	public RoomType() {
		this(1, null, false, 0l);
	}

	public SimpleIntegerProperty getRoomTypeId() {
		return roomTypeId;
	}

	public SimpleStringProperty getRoomNameId() {
		return roomTypeName;
	}

	public SimpleBooleanProperty getDeletedRoomType() {
		return deletedRoomType;
	}

	public SimpleLongProperty getRoomTypePrice() {
		return roomTypePrice;
	}

	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId.set(roomTypeId);;
	}

	public void setRoomNameId(String roomTypeName) {
		this.roomTypeName.set(roomTypeName);;
	}

	public void setDeletedRoomType(boolean deletedRoomType) {
		this.deletedRoomType.set(deletedRoomType);;
	}

	public void setRoomTypePrice(long roomTypePrice) {
		this.roomTypePrice.set(roomTypePrice);;
	}
	
	
	
}
