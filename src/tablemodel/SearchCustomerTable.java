package tablemodel;

import javafx.beans.property.SimpleStringProperty;

public class SearchCustomerTable {
	private SimpleStringProperty id, name, address, governmentId, type;
	
	public SearchCustomerTable(String id, String name, String address, String governmentId, String type) {
		super();
		this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.address = new SimpleStringProperty(address);
		this.governmentId = new SimpleStringProperty(governmentId);
		this.type = new SimpleStringProperty(type);
	}
	
	public SearchCustomerTable() {
		this(null,null,null,null,null);
	}
	
	public SearchCustomerTable(SearchCustomerTable s) {
		this(s.getId().get(), s.getName().get(), s.getAddress().get(), s.getGovernmentId().get(), s.getType().get());
	}

	public SimpleStringProperty getId() {
		return id;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public SimpleStringProperty getAddress() {
		return address;
	}

	public SimpleStringProperty getGovernmentId() {
		return governmentId;
	}

	public SimpleStringProperty getType() {
		return type;
	}

	public void setId(String id) {
		this.id.set(id);
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public void setAddress(String address) {
		this.address.set(address);
	}

	public void setGovernmentId(String governmentId) {
		this.governmentId.set(governmentId);
	}

	public void setType(String type) {
		this.type.set(type);
	}
	
	
}
