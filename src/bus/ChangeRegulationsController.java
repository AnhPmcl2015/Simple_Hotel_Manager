package bus;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.CustomerTypeDAO;
import dao.RoomTypeDAO;
import dao.TemplateDAO;
import dto.RoomType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import tablemodel.ChangeRegulationsTable;

public class ChangeRegulationsController implements Initializable{
	private ObservableList<ChangeRegulationsTable> listChangeRegulations, listTemp;
	private ChangeRegulationsTable temp;
	private boolean flag = false;
	
	@FXML
	private TextField txtRoomTypeId_ChangingRegulations, txtPrice_ChangingRegulations, txtName_Regulations, txtOldValue_ChangingSurcharge,
	txtNewValue_ChangingSurcharge, txtOldValue_ChangingMaximumGuest, txtNewValue_ChangingMaximumGuest, 
	txtOldValue_ChangingRate, txtNewValue_ChangingRate;
	
	@FXML
	private Button btnUpdate_ChangingRegulations, btnDelete_ChangingRegulations, btnAdd_ChangingRegulations, btnOK_ChagingSurcharge,
	btnOK_ChangingMaximumGuest, btnOK_ChangingRate, btnCancel_ChangingRegulations;
	
	@FXML
	private TableView<ChangeRegulationsTable> tableChangingRegulations;
	
	@FXML
	private TableColumn<ChangeRegulationsTable, String> tableColumnName_ChangingRegulations;
	
	@FXML
	private TableColumn<ChangeRegulationsTable, String> tableColumnRoomTypeId_ChangingRegulations;
	
	@FXML
	private TableColumn<ChangeRegulationsTable, String> tableColumnPrice_ChangingRegulations;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		addControls();
		addEvents();
	}
	
	private void addControls() {
		
		txtOldValue_ChangingRate.setText(CustomerTypeDAO.listCustomerType.get(1).getCutomerTypeRate().get()+"");
		txtOldValue_ChangingMaximumGuest.setText(TemplateDAO.maximumCustomers+"");
		txtOldValue_ChangingSurcharge.setText(TemplateDAO.surcharge+"");
		
		btnCancel_ChangingRegulations.setDisable(true);
		btnCancel_ChangingRegulations.setVisible(false);
		
		listChangeRegulations = FXCollections.observableArrayList();
		listTemp = FXCollections.observableArrayList();
		
		tableColumnName_ChangingRegulations.setCellValueFactory(cellData->cellData.getValue().getName());
		tableColumnRoomTypeId_ChangingRegulations.setCellValueFactory(cellData->cellData.getValue().getId());
		tableColumnPrice_ChangingRegulations.setCellValueFactory(cellData->cellData.getValue().getPrice());
		tableChangingRegulations.setItems(listTemp);
	
		fillTable();
		temp = new ChangeRegulationsTable(listTemp.get(0));
		changeDetail();
		
		tableChangingRegulations.getSelectionModel().select(0);
	}
	
	private void fillTable() {
		listChangeRegulations.clear();
		listTemp.clear();
		
		for(RoomType r : RoomTypeDAO.listRoomType) {
			String id = r.getRoomTypeId().get() + "";
			String name = r.getRoomNameId().get() + "";
			String price = r.getRoomTypePrice().get() + "";
			
			listChangeRegulations.add(new ChangeRegulationsTable(id, name, price));
			listTemp.add(new ChangeRegulationsTable(id, name, price));
		}
	}
	
	private void changeDetail() {
		txtRoomTypeId_ChangingRegulations.setText(temp.getId().get());
		txtName_Regulations.setText(temp.getName().get());
		txtPrice_ChangingRegulations.setText(temp.getPrice().get());
	}
	
	private void addEvents() {
		tableChangingRegulations.setOnMouseClicked(e->{
			if(!flag) {
				temp = listTemp.get(tableChangingRegulations.getSelectionModel().getSelectedIndex());
				changeDetail();
			}
		});
		
		btnCancel_ChangingRegulations.setOnMouseClicked(e->{
			flag = false;
			btnCancel_ChangingRegulations.setVisible(false);
			btnCancel_ChangingRegulations.setDisable(true);
			
			btnUpdate_ChangingRegulations.setDisable(false);
			btnDelete_ChangingRegulations.setDisable(false);
			
			fillTable();
			temp = listTemp.get(0);
			changeDetail();
			
			tableChangingRegulations.getSelectionModel().select(0);
		});
		
		btnUpdate_ChangingRegulations.setOnMouseClicked(e->{
			updateRoomTypeDetail();
		});
		
		btnDelete_ChangingRegulations.setOnMouseClicked(e->{
			deteleRoomType();
		});
		
		btnAdd_ChangingRegulations.setOnMouseClicked(e->{
			if(!flag) {
				initAddNewRoomType();
			}else {
				addNewRoomType();
			}
		});
		
		btnOK_ChagingSurcharge.setOnMouseClicked(e->{
			
			try {
				float a = Float.parseFloat(txtNewValue_ChangingSurcharge.getText());
				changeSurcharge(a);
			}catch(Exception e1) {
				Alert dialog = new Alert(AlertType.WARNING);
				dialog.setTitle("Error");
				dialog.setHeaderText("Surcharge must be a number");
				dialog.showAndWait();
			}
		});
		
		txtNewValue_ChangingSurcharge.addEventHandler(KeyEvent.KEY_PRESSED, e->{
			if(e.getCode() == KeyCode.ENTER) {
				try {
					float a = Float.parseFloat(txtNewValue_ChangingSurcharge.getText());
					changeSurcharge(a);
				}catch(Exception e1) {
					Alert dialog = new Alert(AlertType.WARNING);
					dialog.setTitle("Error");
					dialog.setHeaderText("Surcharge must be a number");
					dialog.showAndWait();
				}
			}
		});
		
		btnOK_ChangingMaximumGuest.setOnMouseClicked(e->{
			try {
				Integer a = Integer.parseInt(txtNewValue_ChangingMaximumGuest.getText());
				changeMaximumGuests(a);
			}catch(Exception e1) {
				Alert dialog = new Alert(AlertType.WARNING);
				dialog.setTitle("Error");
				dialog.setHeaderText("Maximun guest must be a number");
				dialog.showAndWait();
			}
		});
		
		txtNewValue_ChangingMaximumGuest.addEventHandler(KeyEvent.KEY_PRESSED, e->{
			if(e.getCode() == KeyCode.ENTER) {
				try {
					Integer a = Integer.parseInt(txtNewValue_ChangingMaximumGuest.getText());
					changeMaximumGuests(a);
				}catch(Exception e1) {
					Alert dialog = new Alert(AlertType.WARNING);
					dialog.setTitle("Error");
					dialog.setHeaderText("Maximun guest must be a number");
					dialog.showAndWait();
				}
			}
		});
		
		btnOK_ChangingRate.setOnMouseClicked(e->{
			try {
				float a = Float.parseFloat(txtNewValue_ChangingRate.getText());
				changeRate(a);
			}catch(Exception e1) {
				Alert dialog = new Alert(AlertType.WARNING);
				dialog.setTitle("Error");
				dialog.setHeaderText("Rate must be a number");
				dialog.showAndWait();
			}
		});
		
		txtNewValue_ChangingRate.addEventHandler(KeyEvent.KEY_PRESSED, e->{
			if(e.getCode() == KeyCode.ENTER) {
				try {
					float a = Float.parseFloat(txtNewValue_ChangingRate.getText());
					changeRate(a);
				}catch(Exception e1) {
					Alert dialog = new Alert(AlertType.WARNING);
					dialog.setTitle("Error");
					dialog.setHeaderText("Rate must be a number");
					dialog.showAndWait();
				}
			}
		});
	}
	
	
	private void changeSurcharge(float a) {
		if(!"".equals(txtNewValue_ChangingSurcharge.getText())) {
			try {
				TemplateDAO.changeSurcharge(a);
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			txtOldValue_ChangingSurcharge.setText(TemplateDAO.surcharge+"");
		}
	}
	
	private void changeMaximumGuests(int a) {
		if(!"".equals(txtNewValue_ChangingMaximumGuest.getText())) {
			try {
				TemplateDAO.changeMaximumGuests(a);
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			txtOldValue_ChangingMaximumGuest.setText(TemplateDAO.maximumCustomers+"");
		}
	}
	
	private void changeRate(float a) {
		if(!"".equals(txtNewValue_ChangingRate.getText())) {
			try {
				CustomerTypeDAO.changeRate(a);
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			txtOldValue_ChangingRate.setText(CustomerTypeDAO.listCustomerType.get(1).getCutomerTypeRate()+"");

		}
	}
	
	
	private void updateRoomTypeDetail() {
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle("Confirm");
		dialog.setHeaderText("Do you want to update info of this room's type?");
		Optional<ButtonType> option = dialog.showAndWait();

		if (option.get() == ButtonType.OK) {
			try {
				int roomTypeId = Integer.parseInt(txtRoomTypeId_ChangingRegulations.getText());
				long price = Long.parseLong(txtPrice_ChangingRegulations.getText());
				RoomType r = new RoomType(roomTypeId, txtName_Regulations.getText(), false, price);
				
				RoomTypeDAO.updateRoomTypeInfo(r);

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			fillTable();
			changeDetail();
			tableChangingRegulations.getSelectionModel().select(0);
		} else if (option.get() == ButtonType.CANCEL) {
			dialog.close();
		}
	}

	private void deteleRoomType() {
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle("Confirm");
		dialog.setHeaderText("Do you want to delete this room's type?");
		Optional<ButtonType> option = dialog.showAndWait();

		if (option.get() == ButtonType.OK) {
			try {
				int roomTypeId = Integer.parseInt(txtRoomTypeId_ChangingRegulations.getText());
				RoomTypeDAO.deleteRoomType(roomTypeId, txtName_Regulations.getText());

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			fillTable();
			changeDetail();
			tableChangingRegulations.getSelectionModel().select(0);
		} else if (option.get() == ButtonType.CANCEL) {
			dialog.close();
		}
	}
	
	private void initAddNewRoomType() {
		flag = true;
		btnUpdate_ChangingRegulations.setDisable(true);
		btnDelete_ChangingRegulations.setDisable(true);
		btnCancel_ChangingRegulations.setDisable(false);
		btnCancel_ChangingRegulations.setVisible(true);
		
		init();
	}
	
	private void init() {
		txtRoomTypeId_ChangingRegulations.setText((RoomTypeDAO.listAllRoomType.size()+1)+"");
		txtPrice_ChangingRegulations.setText("");
		txtName_Regulations.setText("");
	}
	
	private void addNewRoomType() {
		String error = "";
		if("".equals(txtName_Regulations.getText()))
			error+="Name field can't empty\n";
		if("".equals(txtPrice_ChangingRegulations.getText()))	
			error+="Price field can't empty";
		
		if(!"".equals(error)) {
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(error);
			dialog.showAndWait();
			return;
		}
		
		
		int id = Integer.parseInt(txtRoomTypeId_ChangingRegulations.getText());
		RoomType rt = new RoomType(id, txtName_Regulations.getText(), false, Long.parseLong(txtPrice_ChangingRegulations.getText()));
		
		try {
			RoomTypeDAO.addNewRoomType(rt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fillTable();
		init();
	}
}
