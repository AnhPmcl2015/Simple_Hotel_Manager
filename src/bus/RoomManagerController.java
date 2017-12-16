package bus;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.RoomDAO;
import dao.RoomStatusDAO;
import dao.RoomTypeDAO;
import dto.Room;
import dto.RoomStatus;
import dto.RoomType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import tablemodel.RoomManagerTable;

public class RoomManagerController implements Initializable {

	private ObservableList<RoomManagerTable> listRoomManagerTable;
	private ObservableList<RoomManagerTable> listTemp;
	private RoomManagerTable temp;
	private static boolean flag = false;

	@FXML
	private GridPane gridDetail_RoomManager;

	@FXML
	private TextField txtRoomIdDetail_RoomManager, txtRoomNameDetail_RoomManager, txtRoomIdSearch_RoomManager,
			txtRoomNameSearch_RoomManager;

	@FXML
	private ComboBox<String> cbbRoomTypeDetail_RoomManager, cbbRoomTypeSearch_RoomManager;

	@FXML
	private ComboBox<String> cbbRoomStatusDetail_RoomManager;

	@FXML
	private TextArea txtRoomNoteDetail_RoomManager;

	@FXML
	private Button btnUpdate_RoomManager, btnDelete_RoomManager, btnAdd_RoomManager, btnCancel_RoomManager;

	@FXML
	private TableView<RoomManagerTable> tableRoomManager;

	@FXML
	private TableColumn<RoomManagerTable, String> tableColumnRoomId_RoomManager, tableColumnRoomName_RoomManager,
			tableColumnType_RoomManager, tableColumnStatus_RoomManager, tableColumnNote_RoomManager,
			tableColumnPrice_RoomManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		addControls();
		addEvents();
	}

	private void addControls() {

		btnCancel_RoomManager.setDisable(true);
		btnCancel_RoomManager.setVisible(false);
		cbbRoomTypeDetail_RoomManager.setDisable(true);

		ObservableList<String> list = FXCollections.observableArrayList();
		for (int i = 1; i < RoomTypeDAO.listRoomTypeName.size(); i++) {
			list.add(RoomTypeDAO.listRoomTypeName.get(i));
		}
		cbbRoomTypeDetail_RoomManager.setItems(list);

		cbbRoomTypeSearch_RoomManager.setItems(RoomTypeDAO.listRoomTypeName);
		cbbRoomTypeSearch_RoomManager.getSelectionModel().select(0);
		cbbRoomStatusDetail_RoomManager.setItems(RoomStatusDAO.listRoomStatusName);

		listRoomManagerTable = FXCollections.observableArrayList();
		listTemp = FXCollections.observableArrayList();

		tableColumnRoomId_RoomManager.setCellValueFactory(cellData -> cellData.getValue().getRoomId());
		tableColumnRoomName_RoomManager.setCellValueFactory(cellData -> cellData.getValue().getRoomName());
		tableColumnType_RoomManager.setCellValueFactory(cellData -> cellData.getValue().getRoomType());
		tableColumnStatus_RoomManager.setCellValueFactory(cellData -> cellData.getValue().getStatus());
		tableColumnNote_RoomManager.setCellValueFactory(cellData -> cellData.getValue().getNote());
		tableColumnPrice_RoomManager.setCellValueFactory(cellData -> cellData.getValue().getPrice());
		tableRoomManager.setItems(listTemp);

		fillTable();
		temp = new RoomManagerTable(listRoomManagerTable.get(0));
		changeDetail();

		tableRoomManager.getSelectionModel().select(0);
	}

	private void changeDetail() {
		txtRoomIdDetail_RoomManager.setText(temp.getRoomId().get());
		txtRoomNameDetail_RoomManager.setText(temp.getRoomName().get());

		cbbRoomTypeDetail_RoomManager.getSelectionModel().select(temp.getRoomTypeId().get() - 1);
		cbbRoomStatusDetail_RoomManager.getSelectionModel().select(temp.getRoomStatusId().get() - 1);
		txtRoomNoteDetail_RoomManager.setText(temp.getNote().get());
	}

	private void fillTable() {
		listRoomManagerTable.clear();
		listTemp.clear();

		for (Room r : RoomDAO.listRoom) {
			String roomId = r.getRoomId().get();
			String roomName = r.getRoomName().get();
			String roomNote = r.getRoomNote().get();
			String roomType = null;
			String roomPrice = null;
			for (RoomType rt : RoomTypeDAO.listRoomType) {
				if (r.getRoomTypeId().get() == (rt.getRoomTypeId().get())) {
					roomType = rt.getRoomNameId().get();
					roomPrice = rt.getRoomTypePrice().get() + "";
					break;
				}
			}
			int roomTypeId = r.getRoomTypeId().get();
			int roomStatusId = r.getRoomStatusId().get();

			String roomStatus = null;
			for (RoomStatus rs : RoomStatusDAO.listRoomStatus) {
				if (r.getRoomStatusId().get() == (rs.getRoomStatusId().get())) {
					roomStatus = rs.getRoomStatusName().get();
					break;
				}
			}

			listRoomManagerTable.add(new RoomManagerTable(roomId, roomName, roomType, roomPrice, roomNote, roomStatus,
					roomTypeId, roomStatusId));
			listTemp.add(new RoomManagerTable(roomId, roomName, roomType, roomPrice, roomNote, roomStatus, roomTypeId,
					roomStatusId));
		}
	}

	private void addEvents() {
		tableRoomManager.setOnMouseClicked(e -> {
			if (!flag) {
				temp = listTemp.get(tableRoomManager.getSelectionModel().getSelectedIndex());
				changeDetail();
			}
		});

		cbbRoomTypeDetail_RoomManager.getSelectionModel().selectedIndexProperty()
				.addListener(new ChangeListener<Number>() {

					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) {
						// TODO Auto-generated method stub
						txtRoomIdDetail_RoomManager.setText(RoomDAO.countRoomPerType(newValue.intValue() + 1));
					}
				});


		btnUpdate_RoomManager.setOnMouseClicked(e -> {
			updateRoomInfo();
		});

		btnDelete_RoomManager.setOnMouseClicked(e -> {
			deleteRoom(e);
		});

		btnAdd_RoomManager.setOnAction(e -> {
			if (!flag) {
				initAddRoomInfo();
			} else {
				addNewRoom();
			}
		});

		btnCancel_RoomManager.setOnMouseClicked(e -> {
			cancelAddingRoom();
		});
		
		txtRoomIdSearch_RoomManager.addEventHandler(KeyEvent.KEY_PRESSED, e->{

			if(e.getCode() == KeyCode.ENTER) {
				searchRoomFollowingRoomId(txtRoomIdSearch_RoomManager.getText());
			}
		});
		
		txtRoomNameSearch_RoomManager.addEventHandler(KeyEvent.KEY_PRESSED, e->{
			if(e.getCode() == KeyCode.ENTER) {
				searchRoomFollowingRoomName(txtRoomNameSearch_RoomManager.getText());
			}
		});
		
		cbbRoomTypeSearch_RoomManager.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue,
					Number newValue) {
				// TODO Auto-generated method stub
				searchRoomFollowingRoomType(newValue.intValue());
			}
		});
	}

	private void updateRoomInfo() {
		// TODO Auto-generated method stub
		if(temp.getRoomStatusId().get()==2) {
			Alert dialog = new Alert(AlertType.INFORMATION);
			dialog.setTitle("Error");
			dialog.setHeaderText("This room is being rented");
			dialog.showAndWait();
			return;
		}

		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle("Confirm");
		dialog.setHeaderText("Do you want to update info of this room?");
		Optional<ButtonType> option = dialog.showAndWait();

		if (option.get() == ButtonType.OK) {
			try {
				RoomDAO.updateRoomInfo(new Room(txtRoomIdDetail_RoomManager.getText(),
						txtRoomNameDetail_RoomManager.getText(), txtRoomNoteDetail_RoomManager.getText(), false,
						cbbRoomTypeDetail_RoomManager.getSelectionModel().getSelectedIndex() + 1,
						cbbRoomStatusDetail_RoomManager.getSelectionModel().getSelectedIndex() + 1));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			fillTable();
			changeDetail();

		} else if (option.get() == ButtonType.CANCEL) {
			dialog.close();
		}
	}

	private void deleteRoom(MouseEvent e) {
		
		if(temp.getRoomStatusId().get()==2) {
			Alert dialog = new Alert(AlertType.INFORMATION);
			dialog.setTitle("Error");
			dialog.setHeaderText("This room is being rented");
			dialog.showAndWait();
			return;
		}
		
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle("Confirm");
		dialog.setHeaderText("Do you want to delete this room?");
		Optional<ButtonType> option = dialog.showAndWait();

		if (option.get() == ButtonType.OK) {
			try {
				RoomDAO.deleteRoom(temp.getRoomId().get(), temp.getRoomName().get());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			fillTable();
			temp = new RoomManagerTable(listRoomManagerTable.get(0));
			changeDetail();

		} else if (option.get() == ButtonType.CANCEL) {
			dialog.close();
		}
	}

	private void initAddRoomInfo() {
		flag = true;

		cbbRoomTypeDetail_RoomManager.setDisable(false);
		btnUpdate_RoomManager.setDisable(true);
		btnDelete_RoomManager.setDisable(true);

		btnCancel_RoomManager.setDisable(false);
		btnCancel_RoomManager.setVisible(true);
		
		txtRoomIdDetail_RoomManager.setText(RoomDAO.countRoomPerType(1));
		txtRoomNameDetail_RoomManager.setText("");
		cbbRoomTypeDetail_RoomManager.getSelectionModel().select(0);
		cbbRoomStatusDetail_RoomManager.getSelectionModel().select(0);

	}

	private void addNewRoom() {
		Room room = new Room(txtRoomIdDetail_RoomManager.getText(), txtRoomNameDetail_RoomManager.getText(),
				txtRoomNoteDetail_RoomManager.getText(), false, cbbRoomTypeDetail_RoomManager.getSelectionModel().getSelectedIndex()+1, cbbRoomStatusDetail_RoomManager.getSelectionModel().getSelectedIndex()+1);
		try {
			RoomDAO.addNewRoom(room);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		txtRoomIdDetail_RoomManager.setText(RoomDAO.countRoomPerType(1));
		txtRoomNameDetail_RoomManager.setText("");
		cbbRoomTypeDetail_RoomManager.getSelectionModel().select(0);
		cbbRoomStatusDetail_RoomManager.getSelectionModel().select(0);
		
		fillTable();

	}

	private void cancelAddingRoom() {
		cbbRoomTypeDetail_RoomManager.setDisable(true);
		btnUpdate_RoomManager.setDisable(false);
		btnDelete_RoomManager.setDisable(false);

		btnCancel_RoomManager.setDisable(true);
		btnCancel_RoomManager.setVisible(false);

		flag = false;

		fillTable();
		temp = new RoomManagerTable(listRoomManagerTable.get(0));
		changeDetail();
		tableRoomManager.getSelectionModel().select(0);
	}

	private void searchRoomFollowingRoomId(String roomId) {
		txtRoomNameSearch_RoomManager.setText("");
		cbbRoomTypeSearch_RoomManager.getSelectionModel().select(0);
		listTemp.clear();
		if("".equals(roomId))
		{
			for(RoomManagerTable r : listRoomManagerTable) {
				listTemp.add(r);
			}
			temp = new RoomManagerTable(listTemp.get(0));
			changeDetail();
			return;
		}
		listTemp.clear();
		for(RoomManagerTable r:listRoomManagerTable) {
			if(r.getRoomId().get().contains(roomId)) {
				listTemp.add(r);
			}
		}
		if(listTemp.size()>0) {
			temp = new RoomManagerTable(listTemp.get(0));
			changeDetail();
		}
		else {
			txtRoomIdDetail_RoomManager.setText("");
			txtRoomNameDetail_RoomManager.setText("");
			cbbRoomTypeDetail_RoomManager.getSelectionModel().select(0);
			cbbRoomStatusDetail_RoomManager.getSelectionModel().select(0);
			txtRoomNoteDetail_RoomManager.setText("");
		}
		
	}
	
	private void searchRoomFollowingRoomName(String roomName) {
		txtRoomIdSearch_RoomManager.setText("");
		cbbRoomTypeSearch_RoomManager.getSelectionModel().select(0);
		listTemp.clear();
		
		if("".equals(roomName))
		{
			for(RoomManagerTable r : listRoomManagerTable) {
				listTemp.add(r);
			}
			temp = new RoomManagerTable(listTemp.get(0));
			changeDetail();
			return;
		}
		

		for(RoomManagerTable r:listRoomManagerTable) {
			if(r.getRoomName().get().contains(roomName)) {
				listTemp.add(r);
			}
		}
		if(listTemp.size()>0) {
			temp = new RoomManagerTable(listTemp.get(0));
			changeDetail();
		}
		else {
			txtRoomIdDetail_RoomManager.setText("");
			txtRoomNameDetail_RoomManager.setText("");
			cbbRoomTypeDetail_RoomManager.getSelectionModel().select(0);
			cbbRoomStatusDetail_RoomManager.getSelectionModel().select(0);
			txtRoomNoteDetail_RoomManager.setText("");
		}
	}
	
	private void searchRoomFollowingRoomType(int index) {
		txtRoomNameSearch_RoomManager.setText("");
		txtRoomIdSearch_RoomManager.setText("");
		listTemp.clear();
		if(index == 0) {
			for(RoomManagerTable r : listRoomManagerTable) {
				listTemp.add(r);
			}
			temp = new RoomManagerTable(listTemp.get(0));
			changeDetail();
			return;
		}
		
		listTemp.clear();
		for(RoomManagerTable r:listRoomManagerTable) {
			if(r.getRoomTypeId().get() == index) {
				listTemp.add(r);
			}
		}
		if(listTemp.size()>0) {
			temp = new RoomManagerTable(listTemp.get(0));
			changeDetail();
		}
		else {
			txtRoomIdDetail_RoomManager.setText("");
			txtRoomNameDetail_RoomManager.setText("");
			cbbRoomTypeDetail_RoomManager.getSelectionModel().select(0);
			cbbRoomStatusDetail_RoomManager.getSelectionModel().select(0);
			txtRoomNoteDetail_RoomManager.setText("");
		}
	}
}
