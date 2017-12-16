package bus;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.StaffDAO;
import dto.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import tablemodel.StaffManagerTable;

public class StaffManagerController implements Initializable {

	private ObservableList<StaffManagerTable> listStaffManagerTable;
	private ObservableList<StaffManagerTable> listTemp;
	private static boolean flag = false;
	private StaffManagerTable temp;

	@FXML
	private GridPane gridDetail_StaffManager;

	@FXML
	private TextField txtStaffIdDetail_StaffManager, txtNameDetail_StaffManager, txtGovernmentIdDetail_StaffManager,
			txtUsernameDetail_StaffManager, txtPasswordDetail_StaffManager, txtStaffIdSearch_StaffManager,
			txtNameSearch_StaffManager;

	@FXML
	private ComboBox<String> cbbStaffTypeDetail_StaffManager;

	@FXML
	private TextArea txtAddressDetail_StaffManager;

	@FXML
	private Button btnUpdate_StaffManager, btnAdd_StaffManager, btnCancel_StaffManager;

	@FXML
	private TableView<StaffManagerTable> tableStaffManager;

	@FXML
	private TableColumn<StaffManagerTable, String> tableColumnStaffId_StaffManager, tableColumnName_StaffManager,
			tableColumnGovernmentId_StaffManager, tableColumnPosition_StaffManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		addControls();
		addEvents();
	}

	private void addControls() {
		txtGovernmentIdDetail_StaffManager.setDisable(true);
		btnCancel_StaffManager.setDisable(true);
		btnCancel_StaffManager.setVisible(false);

		listStaffManagerTable = FXCollections.observableArrayList();
		listTemp = FXCollections.observableArrayList();

		ObservableList<String> listStaffType = FXCollections.observableArrayList("Receptionis", "Manager");
		cbbStaffTypeDetail_StaffManager.setItems(listStaffType);
		cbbStaffTypeDetail_StaffManager.getSelectionModel().select(0);

		tableColumnStaffId_StaffManager.setCellValueFactory(cellData -> cellData.getValue().getStaffId());
		tableColumnName_StaffManager.setCellValueFactory(cellData -> cellData.getValue().getStaffName());
		tableColumnGovernmentId_StaffManager.setCellValueFactory(cellData -> cellData.getValue().getGovernmentID());
		tableColumnPosition_StaffManager.setCellValueFactory(cellData -> cellData.getValue().getPosition());
		tableStaffManager.setItems(listTemp);

		fillTable();
		temp = new StaffManagerTable(listTemp.get(0));
		changeDetail();

		tableStaffManager.getSelectionModel().select(0);
	}

	private void changeDetail() {
		txtStaffIdDetail_StaffManager.setText(temp.getStaffId().get());
		txtNameDetail_StaffManager.setText(temp.getStaffName().get());
		txtGovernmentIdDetail_StaffManager.setText(temp.getGovernmentID().get());
		txtUsernameDetail_StaffManager.setText(temp.getUsername().get());
		txtPasswordDetail_StaffManager.setText(temp.getPassword().get());
		txtAddressDetail_StaffManager.setText(temp.getAddress().get());
		if ("Manager".equalsIgnoreCase(temp.getPosition().get()))
			cbbStaffTypeDetail_StaffManager.getSelectionModel().select(1);
		else
			cbbStaffTypeDetail_StaffManager.getSelectionModel().select(0);

		if (cbbStaffTypeDetail_StaffManager.getSelectionModel().getSelectedIndex() == 1)
			cbbStaffTypeDetail_StaffManager.setDisable(true);
		else
			cbbStaffTypeDetail_StaffManager.setDisable(false);

	}

	private void fillTable() {
		listStaffManagerTable.clear();
		listTemp.clear();

		for (Staff s : StaffDAO.listStaff) {
			String staffId = s.getStaffId().get();
			String staffName = s.getPeopleName().get();
			String governmentID = s.getGovernmentId().get();
			String position = "";
			if (s.getIsManager().get()) {
				position = "Manager";
			} else
				position = "Receptionist";

			String username = s.getUsername().get();
			String password = s.getPassword().get();
			String address = s.getPeopleAddress().get();

			listStaffManagerTable.add(
					new StaffManagerTable(staffId, staffName, governmentID, position, username, password, address));
			listTemp.add(
					new StaffManagerTable(staffId, staffName, governmentID, position, username, password, address));
		}
	}

	private void addEvents() {
		tableStaffManager.setOnMouseClicked(e -> {
			if (!flag) {
				temp = listTemp.get(tableStaffManager.getSelectionModel().getSelectedIndex());
				changeDetail();
			}
		});

		btnCancel_StaffManager.setOnMouseClicked(e -> {
			btnCancel_StaffManager.setDisable(true);
			btnCancel_StaffManager.setVisible(false);

			txtGovernmentIdDetail_StaffManager.setEditable(false);
			flag = false;
			btnUpdate_StaffManager.setDisable(false);

			fillTable();
			temp = new StaffManagerTable(listTemp.get(0));
			changeDetail();
			tableStaffManager.getSelectionModel().select(0);
		});

		btnUpdate_StaffManager.setOnMouseClicked(e -> {
			updateStaffInfo();
		});

		btnAdd_StaffManager.setOnMouseClicked(e -> {
			if (!flag) {
				initAddStaffInfo();
			} else {
				addNewStaff();
			}
		});
		
		txtNameSearch_StaffManager.addEventHandler(KeyEvent.KEY_PRESSED, e->{
			if(e.getCode() == KeyCode.ENTER) {
				searchStaffFollowingStaffName(txtNameSearch_StaffManager.getText());
			}
		});
		
		txtStaffIdDetail_StaffManager.addEventHandler(KeyEvent.KEY_PRESSED, e->{
			if(e.getCode() == KeyCode.ENTER) {
				searchStaffFollowingStaffId(txtStaffIdDetail_StaffManager.getText());
			}
		});
	}

	private void updateStaffInfo() {
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle("Confirm");
		dialog.setHeaderText("Do you want to update info of this staff?");
		Optional<ButtonType> option = dialog.showAndWait();

		if (option.get() == ButtonType.OK) {
			try {
				boolean isManager = false;
				if ("Manager".equalsIgnoreCase(temp.getPosition().get()))
					isManager = true;
				StaffDAO.updateStaffInfo(new Staff(txtNameDetail_StaffManager.getText(),
						txtAddressDetail_StaffManager.getText(), txtGovernmentIdDetail_StaffManager.getText(),
						txtStaffIdDetail_StaffManager.getText(), txtUsernameDetail_StaffManager.getText(), isManager,
						txtPasswordDetail_StaffManager.getText()) {
				});

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			fillTable();
			changeDetail();
			tableStaffManager.getSelectionModel().select(0);
		} else if (option.get() == ButtonType.CANCEL) {
			dialog.close();
		}
	}

	private void initAddStaffInfo() {
		flag = true;
		init();
		btnUpdate_StaffManager.setDisable(true);
		btnCancel_StaffManager.setDisable(false);
		btnCancel_StaffManager.setVisible(true);
	}
	
	private void init() {
		txtGovernmentIdDetail_StaffManager.setText("");
		txtGovernmentIdDetail_StaffManager.setDisable(false);
		String t = "St_";
		int count = StaffDAO.listStaff.size()+1;
		String countString = count+"";
		if(countString.length()==1) {
			t+="00";
		}else if(countString.length()==2) {
			t+="0";
		}
		t+=countString;
		txtStaffIdDetail_StaffManager.setText(t);
		txtUsernameDetail_StaffManager.setText("");
		txtPasswordDetail_StaffManager.setText("");
		txtAddressDetail_StaffManager.setText("");
		cbbStaffTypeDetail_StaffManager.getSelectionModel().select(0);
		txtNameDetail_StaffManager.setText("");
		cbbStaffTypeDetail_StaffManager.setDisable(false);
	}

	private void addNewStaff() {
		String error = "";
		if(txtNameDetail_StaffManager.getText().equals("")) {
			error += "Name can't empty\n";
		}
		if(txtGovernmentIdDetail_StaffManager.getText().equals("")) {
			error+= "Government id can't empty\n";
		}
		for(Staff s : StaffDAO.listStaff) {
			if(txtGovernmentIdDetail_StaffManager.getText().equals(s.getGovernmentId().get())) {
				error += "This staff is currently working";
				break;
			}
		}
		
		if(!"".equals(error)) {
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(error);
			dialog.showAndWait();
			return;
		}
		
		boolean isManager = false;
		if(cbbStaffTypeDetail_StaffManager.getSelectionModel().getSelectedIndex()==1)
			isManager = true;
		Staff staff = new Staff(txtNameDetail_StaffManager.getText(),
				txtAddressDetail_StaffManager.getText(), txtGovernmentIdDetail_StaffManager.getText(),
				txtStaffIdDetail_StaffManager.getText(), txtUsernameDetail_StaffManager.getText(), isManager,
				txtPasswordDetail_StaffManager.getText()) {};
				
		try {
			StaffDAO.addNewStaf(staff);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fillTable();
		init();
	}

	private void searchStaffFollowingStaffName(String staffName) {
		txtStaffIdSearch_StaffManager.setText("");
		listTemp.clear();
		
		if("".equals(staffName)) {
			for(StaffManagerTable r : listStaffManagerTable) {
				listTemp.add(r);
			}
			temp = new StaffManagerTable(listTemp.get(0));
			changeDetail();
			return;
		}
		
		for(StaffManagerTable s : listStaffManagerTable) {
			if(s.getStaffName().get().contains(staffName)) {
				listTemp.add(s);
			}
		}
		
		if(listTemp.size() > 0) {
			temp = new StaffManagerTable(listTemp.get(0));
			changeDetail();
		}else {
			init();
			txtGovernmentIdDetail_StaffManager.setDisable(true);
		}
	}
	
	private void searchStaffFollowingStaffId(String staffId) {
		txtNameSearch_StaffManager.setText("");
		listTemp.clear();
		
		if("".equals(staffId)) {
			for(StaffManagerTable r : listStaffManagerTable) {
				listTemp.add(r);
			}
			temp = new StaffManagerTable(listTemp.get(0));
			changeDetail();
			return;
		}
		
		for(StaffManagerTable s : listStaffManagerTable) {
			if(s.getStaffId().get().equals(staffId)) {
				listTemp.add(s);
			}
		}
		
		if(listTemp.size() > 0) {
			temp = new StaffManagerTable(listTemp.get(0));
			changeDetail();
		}else {
			init();
			txtGovernmentIdDetail_StaffManager.setDisable(true);
		}
	}
}
