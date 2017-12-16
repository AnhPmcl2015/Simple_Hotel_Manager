package dto;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Room {
	private SimpleStringProperty roomId;
	private SimpleStringProperty roomName;
	private SimpleStringProperty roomNote;
	private SimpleBooleanProperty deletedRoom;
	private SimpleIntegerProperty roomTypeId;
	private SimpleIntegerProperty roomStatusId;
	
	public Room(String roomId, String roomName, String roomNote, boolean deletedRoom, int roomTypeId, int roomStatusId) {
		super();
		this.roomId = new SimpleStringProperty(roomId);
		this.roomName = new SimpleStringProperty(roomName);
		this.roomNote = new SimpleStringProperty(roomNote);
		this.deletedRoom = new SimpleBooleanProperty(deletedRoom);
		this.roomTypeId = new SimpleIntegerProperty(roomTypeId);
		this.roomStatusId = new SimpleIntegerProperty(roomStatusId);
	}
	
	public Room(Room room) {
		this(room.getRoomId().get(), room.getRoomName().get(), room.getRoomNote().get(), room.getDeletedRoom().get(), room.getRoomTypeId().get(), room.getRoomStatusId().get());
	}
	
	public Room() {
		this(null,null,null,false,1,1);
	}

	public SimpleStringProperty getRoomId() {
		return roomId;
	}

	public SimpleStringProperty getRoomName() {
		return roomName;
	}

	public SimpleStringProperty getRoomNote() {
		return roomNote;
	}

	public SimpleBooleanProperty getDeletedRoom() {
		return deletedRoom;
	}

	public SimpleIntegerProperty getRoomTypeId() {
		return roomTypeId;
	}

	public SimpleIntegerProperty getRoomStatusId() {
		return roomStatusId;
	}

	public void setRoomId(String roomId) {
		this.roomId.set(roomId);
	}

	public void setRoomName(String roomName) {
		this.roomName.set(roomName);
	}

	public void setRoomNote(String roomNote) {
		this.roomNote.set(roomNote);
	}

	public void setDeletedRoom(boolean deletedRoom) {
		this.deletedRoom.set(deletedRoom);
	}

	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId.set(roomTypeId);
	}

	public void setRoomStatusId(int roomStatusId) {
		this.roomStatusId.set(roomStatusId);
	}
	
	
	public static ObservableList<Room> searchRoom(ObservableList<Room> rooms, String roomName) {
		ObservableList<Room> list= FXCollections.observableArrayList();
		if("".equals(roomName.trim()))
			return rooms;
		for(Room room : rooms) {
			if(roomName.trim().equalsIgnoreCase(room.getRoomName().get())) {
				list.add(room);
			}
		}
		return list;
	}
	
	public static ObservableList<Room> searchRoom(ObservableList<Room> rooms, long price){
		ObservableList<Room> list= FXCollections.observableArrayList();
		
		return list;
	}
	
	
}
