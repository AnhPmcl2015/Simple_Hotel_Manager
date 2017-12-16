package tablemodel;

import javafx.beans.property.SimpleStringProperty;

public class ChangeRegulationsTable {
	private SimpleStringProperty id, name, price;
	
	public ChangeRegulationsTable(String id, String name, String price) {
		super();
		this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.price = new SimpleStringProperty(price);
	}
	
	public ChangeRegulationsTable() {
		this(null,null,null);
	}

	public ChangeRegulationsTable(ChangeRegulationsTable newValue) {
		this(newValue.getId().get(), newValue.getName().get(), newValue.getPrice().get());
	}
	
	public SimpleStringProperty getId() {
		return id;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public SimpleStringProperty getPrice() {
		return price;
	}

	public void setId(String id) {
		this.id.set(id);
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public void setPrice(String price) {
		this.price.set(price);
	}
	
	
	
	
}
