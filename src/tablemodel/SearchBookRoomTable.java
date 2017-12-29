package tablemodel;

import javafx.beans.property.SimpleStringProperty;

public class SearchBookRoomTable {
	private SimpleStringProperty id, staff, customer, date, customerID;

	public SearchBookRoomTable(String id, String staff, String customer,
			String date, String customerID) {
		super();
		this.id = new SimpleStringProperty(id);
		this.staff = new SimpleStringProperty(staff);
		this.customer = new SimpleStringProperty(customer);
		this.date = new SimpleStringProperty(date);
		this.customerID = new SimpleStringProperty(customerID);
	}
	
	public SearchBookRoomTable() {
		this(null,null,null,null,null);
	}
	
	public SearchBookRoomTable(SearchBookRoomTable value) {
		this(value.getId().get(), value.getStaff().get(), value.getCustomer().get(), value.getDate().get(), value.getCustomerId().get());
	}

	public SimpleStringProperty getId() {
		return id;
	}
	
	public SimpleStringProperty getCustomerId() {
		return customerID;
	}

	public SimpleStringProperty getStaff() {
		return staff;
	}

	public SimpleStringProperty getCustomer() {
		return customer;
	}

	public SimpleStringProperty getDate() {
		return date;
	}

	public void setId(String id) {
		this.id.set(id);
	}

	public void setStaff(String staff) {
		this.staff.set(staff);
	}

	public void setCustomer(String customer) {
		this.customer.set(customer);
	}

	public void setDate(String date) {
		this.date.set(date);
	}
	
	public void setCustomerID(String customerID) {
		this.customerID.set(customerID);
	}
	
	
	
}
