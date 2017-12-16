package dto;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerType {
	private SimpleIntegerProperty customerTypeId;
	private SimpleStringProperty customerTypeName;
	private SimpleFloatProperty cutomerTypeRate;
	
	public CustomerType(int customerTypeId, String customerTypeName,
			float cutomerTypeRate) {
		super();
		this.customerTypeId = new SimpleIntegerProperty(customerTypeId);
		this.customerTypeName = new SimpleStringProperty(customerTypeName);
		this.cutomerTypeRate = new SimpleFloatProperty(cutomerTypeRate);
	}
	
	public CustomerType() {
		this(1,null,1);
	}
	public SimpleIntegerProperty getCustomerTypeId() {
		return customerTypeId;
	}
	public SimpleStringProperty getCustomerTypeName() {
		return customerTypeName;
	}
	public SimpleFloatProperty getCutomerTypeRate() {
		return cutomerTypeRate;
	}
	public void setCustomerTypeId(int customerTypeId) {
		this.customerTypeId.set(customerTypeId);;
	}
	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName.set(customerTypeName);;
	}
	public void setCutomerTypeRate(float cutomerTypeRate) {
		this.cutomerTypeRate.set(cutomerTypeRate);;
	}
}
