package dto;

import javafx.beans.property.SimpleStringProperty;

public abstract class People {
	protected SimpleStringProperty peopleName, peopleAddress;
	protected SimpleStringProperty governmentId;
	
	
	public People(String peopleName, String peopleAddress, String governmentId) {
		super();
		this.peopleName = new SimpleStringProperty(peopleName);
		this.peopleAddress = new SimpleStringProperty(peopleAddress);
		this.governmentId = new SimpleStringProperty(governmentId);
	}
	
	public People(People newValue) {
		this(newValue.getPeopleName().get(), newValue.getPeopleAddress().get(), newValue.getGovernmentId().get());
	}
	
	public People() {
		this(null,null,null);
	}

	public SimpleStringProperty getPeopleName() {
		return peopleName;
	}

	public SimpleStringProperty getPeopleAddress() {
		return peopleAddress;
	}

	public SimpleStringProperty getGovernmentId() {
		return governmentId;
	}

	public void setPeopleName(String peopleName) {
		this.peopleName.set(peopleName);;
	}

	public void setPeopleAddress(String peopleAddress) {
		this.peopleAddress.set(peopleAddress);;
	}

	public void setGovernmentId(String governmentId) {
		this.governmentId.set(governmentId);;
	}
	
	
	
	
}
