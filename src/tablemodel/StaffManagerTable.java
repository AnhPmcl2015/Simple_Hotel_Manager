package tablemodel;

import javafx.beans.property.SimpleStringProperty;

public class StaffManagerTable {
	private SimpleStringProperty staffId, staffName, governmentID, position, username, password, address;

	public StaffManagerTable(String staffId, String staffName,
			String governmentID, String position, String username, String password, String address) {
		super();
		this.staffId = new SimpleStringProperty(staffId);
		this.staffName = new SimpleStringProperty(staffName);
		this.governmentID = new SimpleStringProperty(governmentID);
		this.position = new SimpleStringProperty(position);
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
		this.address = new SimpleStringProperty(address);
	}
	
	public StaffManagerTable() {
		this(null,null,null,null,null,null,null);
	}
	
	public StaffManagerTable(StaffManagerTable newValue) {
		this(newValue.getStaffId().get(), newValue.getStaffName().get(),newValue.getGovernmentID().get(),newValue.getPosition().get(), newValue.getUsername().get(), newValue.getPassword().get(), newValue.getAddress().get());
	}

	public SimpleStringProperty getStaffId() {
		return staffId;
	}

	public SimpleStringProperty getStaffName() {
		return staffName;
	}

	public SimpleStringProperty getGovernmentID() {
		return governmentID;
	}

	public SimpleStringProperty getUsername() {
		return username;
	}
	
	public SimpleStringProperty getPassword() {
		return password;
	}
	
	public SimpleStringProperty getAddress() {
		return address;
	}
	
	public SimpleStringProperty getPosition() {
		return position;
	}

	public void setStaffId(String staffId) {
		this.staffId.set(staffId);
	}

	public void setStaffName(String staffName) {
		this.staffName.set(staffName);;
	}

	public void setGovernmentID(String governmentID) {
		this.governmentID.set(governmentID);;
	}

	public void setPosition(String position) {
		this.position.set(position);;
	}
	
	public void setUsername(String username) {
		this.username.set(username);
	}
	
	public void setPassword(String password) {
		this.password.set(password);
	}
	
	public void setAddress(String address) {
		this.address.set(address);
	}
	
}
