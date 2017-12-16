package dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer extends People{
	private SimpleStringProperty customerId;
	private SimpleIntegerProperty customerTypeId;
	
	public Customer(String peopleName, String peopleAddress,
			String governmentId, String customerId, int customerTypeId) {
		super(peopleName, peopleAddress, governmentId);
		this.customerId = new SimpleStringProperty(customerId);
		this.customerTypeId = new SimpleIntegerProperty(customerTypeId);
	}
	
	public Customer(Customer newValue) {
		this(newValue.getPeopleName().get(), newValue.getPeopleAddress().get(), newValue.getGovernmentId().get(), 
				newValue.getCustomerId().get(),newValue.getCustomerTypeId().get());
	}
	
	public Customer() {
		this(null,null,null,null,1);
	}

	public SimpleStringProperty getCustomerId() {
		return customerId;
	}

	public SimpleIntegerProperty getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerId(String customerId) {
		this.customerId.set(customerId);
	}

	public void setCustomerTypeId(int customerTypeId) {
		this.customerTypeId.set(customerTypeId);
	}
	
}
