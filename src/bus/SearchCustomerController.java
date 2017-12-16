package bus;

import java.net.URL;
import java.util.ResourceBundle;

import dao.CustomerDAO;
import dto.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import tablemodel.SearchCustomerTable;

public class SearchCustomerController implements Initializable{

	private ObservableList<SearchCustomerTable> listSearchCustomerTable, listTemp;
	private SearchCustomerTable temp;
	
	@FXML
	private TextField txtNameSearch_SearchCustomer, txtGovernmentIDSearcg_SearchCustomer, txtID_SearchCustomer,
	txtType_SearchCustomer, txtName_SearchCustomer, txtGovermentIDDetail_Searchcustomer, 
	txtAddress_SearchCustomer;
	
	@FXML 
	private TableView<SearchCustomerTable> tableSearchCustomer;
	
	@FXML
	private TableColumn<SearchCustomerTable, String> tableColumnID, tableColumnName, tableColumnAddress,
	tableColumnGovernmentID, tableColumnCustomerType;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		addControls();
		addEvents();
	}
	
	private void addControls() {
		listSearchCustomerTable = FXCollections.observableArrayList();
		listTemp = FXCollections.observableArrayList();
		
		tableColumnID.setCellValueFactory(cellData->cellData.getValue().getId());
		tableColumnName.setCellValueFactory(cellData->cellData.getValue().getName());
		tableColumnAddress.setCellValueFactory(cellData->cellData.getValue().getAddress());
		tableColumnGovernmentID.setCellValueFactory(cellData->cellData.getValue().getGovernmentId());
		tableColumnCustomerType.setCellValueFactory(cellData->cellData.getValue().getType());
		
		tableSearchCustomer.setItems(listTemp);
		
		fillTable();
		temp = new SearchCustomerTable(listTemp.get(0));
		changeDetail();

		tableSearchCustomer.getSelectionModel().select(0);
	}
	
	private void fillTable() {
		listSearchCustomerTable.clear();
		listTemp.clear();
		
		for(Customer c : CustomerDAO.listCustomer) {
			String id = c.getCustomerId().get();
			String name = c.getPeopleName().get();
			String address = c.getPeopleAddress().get();
			String governmentId = c.getGovernmentId().get();
			
			String type = "Native";
			if(c.getCustomerTypeId().get() == 2)
				type = "Foreign";
			listSearchCustomerTable.add(new SearchCustomerTable(id, name, address, governmentId, type));
			listTemp.add(new SearchCustomerTable(id, name, address, governmentId, type));
		}
	}

	private void changeDetail() {
		txtID_SearchCustomer.setText(temp.getId().get());
		txtType_SearchCustomer.setText(temp.getType().get());
		txtName_SearchCustomer.setText(temp.getName().get());
		txtGovermentIDDetail_Searchcustomer.setText(temp.getGovernmentId().get());
		txtAddress_SearchCustomer.setText(temp.getAddress().get());
	}
	
	private void addEvents() {
		tableSearchCustomer.setOnMouseClicked(e->{
			temp = listTemp.get(tableSearchCustomer.getSelectionModel().getSelectedIndex());
			changeDetail();
		});
		
		txtNameSearch_SearchCustomer.addEventHandler(KeyEvent.KEY_PRESSED, e->{
			if(e.getCode() == KeyCode.ENTER)
				searchCustomerFollowingName(txtNameSearch_SearchCustomer.getText());
		});
		
		txtGovernmentIDSearcg_SearchCustomer.addEventHandler(KeyEvent.KEY_PRESSED, e->{
			if(e.getCode() == KeyCode.ENTER)
				searchCustomerFollowingGovernmentID(txtGovernmentIDSearcg_SearchCustomer.getText());
		});
	}
	
	private void searchCustomerFollowingName(String name) {
		txtGovernmentIDSearcg_SearchCustomer.setText("");
		listTemp.clear();
		
		if("".equals(name)) {
			for(SearchCustomerTable r : listSearchCustomerTable) {
				listTemp.add(r);
			}
			temp = listTemp.get(0);
			changeDetail();
			return;
		}
		
		for(SearchCustomerTable s : listSearchCustomerTable) {
			if(s.getName().get().contains(name)) {
				listTemp.add(s);
			}
		}
		
		if(listTemp.size() == 0)
			return;
		temp = listTemp.get(0);
		changeDetail();
	}
	
	private void searchCustomerFollowingGovernmentID(String id) {
		txtNameSearch_SearchCustomer.setText("");
		listTemp.clear();
		
		if("".equals(id)) {
			for(SearchCustomerTable r : listSearchCustomerTable) {
				listTemp.add(r);
			}
			temp = listTemp.get(0);
			changeDetail();
			return;
		}
		
		for(SearchCustomerTable s : listSearchCustomerTable) {
			if(s.getGovernmentId().get().contains(id)) {
				listTemp.add(s);
			}
		}
		
		if(listTemp.size() == 0)
			return;
		temp = listTemp.get(0);
		changeDetail();
	}

}
