package dto;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public abstract class Staff extends People{
	private SimpleStringProperty staffId, username, password;
	private SimpleBooleanProperty isManager;
	
	

	public Staff(String peopleName, String peopleAddress, String governmentId,
			String staffId, String username, boolean isManager, String password) {
		super(peopleName, peopleAddress, governmentId);
		this.staffId = new SimpleStringProperty(staffId);
		this.username = new SimpleStringProperty(username);
		this.isManager = new SimpleBooleanProperty(isManager);
		this.password = new SimpleStringProperty(password);
	}

	public Staff(Staff newValue) {
		this(newValue.getPeopleName().get(),newValue.getPeopleAddress().get(),newValue.getGovernmentId().get(),
				newValue.getStaffId().get(), newValue.getUsername().get(),newValue.getIsManager().get(), newValue.getPassword().get());
	}

	public Staff() {
		this(null,null,null,null,null,false,null);
	}


	public SimpleStringProperty getStaffId() {
		return staffId;
	}


	public SimpleStringProperty getUsername() {
		return username;
	}
	
	public SimpleStringProperty getPassword() {
		return password;
	}


	public SimpleBooleanProperty getIsManager() {
		return isManager;
	}


	public void setStaffId(String staffId) {
		this.staffId.set(staffId);
	}


	public void setUsername(String username) {
		this.username.set(username);
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	public void setIsManager(boolean isManager) {
		this.isManager.set(isManager);
	}
	
	
	/**
	 * add new order detail to databse
	 * @param bookRoom
	 * 		current order need to be added
	 * @param bookRoomDetail
	 * 		current order detail need to be added
	 */
	public void addBookRoom(BookRoom bookRoom, ObservableList<BookRoomDetail> bookRoomDetail) {
		
	}
	
	/**
	 * update new room status
	 * @param roomId
	 * 		room need to be changed
	 * @param newRoomStatusId
	 * 		new status of room
	 */
	
	public void updateRoomStatus(String roomId, int newRoomStatusId) {
		
	}
	
	
}
