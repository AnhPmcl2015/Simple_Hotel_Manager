package bus;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import dao.BookRoomDAO;
import dao.BookRoomDetailDAO;
import dao.CustomerDAO;
import dao.CustomerTypeDAO;
import dao.RoomDAO;
import dao.RoomTypeDAO;
import dao.TemplateDAO;
import dto.BookRoom;
import dto.BookRoomDetail;
import dto.Customer;
import dto.Room;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import tablemodel.BookRoomTable;

public class BookRoomController implements Initializable{

	private ObservableList<BookRoomTable> listBookRoomTable;
	private boolean flag = false;
	private ObservableList<Room> listRoom;
	private ObservableList<BookRoomDetail> listBookRoomDetail;

	@FXML
	private GridPane gridDetail_BookRoom;
	
	@FXML
	private TextField txtCustomerId_BookRoom, txtName_BookRoom, txtGovernmentIdDetail_BookRoom, txtDate_BookRoom;
	
	@FXML
	private ComboBox<String> cbbCustomerTypeDetail_BookRoom;
	
	@FXML
	private TextArea txtAddressDetail_BookRoom;
	
	@FXML
	private Button btnOK_BookRoom, btnCancel_BookRoom;
	
	@FXML
	private ScrollPane scrollPane;
	
	@FXML
	private FlowPane flowPane;
	
	@FXML
	private TableView<BookRoomTable> tableBookRoom;
	
	@FXML
	private TableColumn<BookRoomTable, String> tableColumnRoomName_BookRoom, tableColumnNumberOfGuest_BookRoom;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		addControls();
		addEvents();
	}
	
	private void addControls() {

		init();
		listRoom = FXCollections.observableArrayList();
		listBookRoomTable = FXCollections.observableArrayList();
		listBookRoomDetail = FXCollections.observableArrayList();
		
		tableColumnRoomName_BookRoom.setCellValueFactory(cellData -> cellData.getValue().getRoomName());
		tableColumnNumberOfGuest_BookRoom.setCellValueFactory(cellData->cellData.getValue().getNumberOfGuests());
		tableBookRoom.setItems(listBookRoomTable);
		
		tableColumnNumberOfGuest_BookRoom.setCellFactory(TextFieldTableCell.<BookRoomTable> forTableColumn());
		tableColumnNumberOfGuest_BookRoom.setOnEditCommit((CellEditEvent<BookRoomTable, String> event) -> {
	            TablePosition<BookRoomTable, String> pos = event.getTablePosition();
	            int num = Integer.parseInt(event.getNewValue());
	            if(Integer.parseInt(event.getNewValue()) > 3)
	            	num = 3;
	            else if(Integer.parseInt(event.getNewValue()) < 1)
	            	num = 1;
	            listBookRoomTable.get(pos.getRow()).setNumberOfGuests(num+"");
	        });
		
		fillRoom();
	}
	
	private void init() {
		cbbCustomerTypeDetail_BookRoom.setItems(CustomerTypeDAO.listCustomerTypeName);
		cbbCustomerTypeDetail_BookRoom.getSelectionModel().select(0);
		
		txtDate_BookRoom.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd - MM - yyyy")));
		txtCustomerId_BookRoom.setText("KH_" + (CustomerDAO.listCustomer.size() + 1));
		txtGovernmentIdDetail_BookRoom.setText("");
		txtName_BookRoom.setText("");
		txtAddressDetail_BookRoom.setText("");
		
		flag = false;
	}
	
	private void fillRoom() {
		flowPane.getChildren().clear();
		for(int i = 1; i < RoomTypeDAO.listRoomTypeName.size(); i++) {
			FlowPane flow = new FlowPane();
			flow.setHgap(20);
			flow.setVgap(20);
			flow.setPadding(new Insets(15));
			flow.setPrefSize(600, 150);
			
			for(int j = 0; j < RoomDAO.listRoom.size(); j++) {
				if(RoomDAO.listRoom.get(j).getRoomTypeId().get() == i) {
					ToggleButton toggle = new ToggleButton(RoomDAO.listRoom.get(j).getRoomName().get());
					toggle.setPrefSize(100, 30);
					if(RoomDAO.listRoom.get(j).getRoomStatusId().get()==2) {
						toggle.setStyle("-fx-background-color: #FFFF99");
					}else if(RoomDAO.listRoom.get(j).getRoomStatusId().get()==3) {
						toggle.setStyle("-fx-background-color: #CC6666");
					}
					toggle.setUserData(RoomDAO.listRoom.get(j).getRoomName());
					toggle.setOnAction((e)->clickToggleButton(e)); 
					flow.getChildren().add(toggle);
				}
			}
			
			TitledPane title = new TitledPane(RoomTypeDAO.listRoomTypeName.get(i), flow);
			flowPane.getChildren().add(title);
		}
	}
	
	private void clickToggleButton(javafx.event.ActionEvent e) {
		ToggleButton btn = (ToggleButton) e.getSource();
		
		String name = ((SimpleStringProperty) btn.getUserData()).get();
		
		for(Room r : RoomDAO.listRoom) {
			if(name.equals(r.getRoomName().get())) {
				switch(r.getRoomStatusId().get()) {
				case 1:{
					
					if(btn.isSelected())
					{
						listBookRoomTable.add(new BookRoomTable(name, "1"));
					}
					else {
						for(BookRoomTable b : listBookRoomTable) {
							if(name.equals(b.getRoomName().get())) {
								listBookRoomTable.remove(b);
								break;
							}
						}
					}
					break;
				}
				case 2: case 3:{
					Alert dialog = new Alert(AlertType.WARNING);
					dialog.setTitle("Error");
					dialog.setHeaderText("Room is not available!!!!!!");
					dialog.showAndWait();
					break;
				}
				}
				break;
			}
		}
	}
	
	private void addEvents() {
		btnCancel_BookRoom.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
			cancelOrder();
		});
		
		txtGovernmentIdDetail_BookRoom.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if("".equals(newValue))
					return;
				for(Customer c : CustomerDAO.listCustomer) {
					if(newValue.equals(c.getGovernmentId().get()))
					{
						txtCustomerId_BookRoom.setText(c.getCustomerId().get());
						txtName_BookRoom.setText(c.getPeopleName().get());
						cbbCustomerTypeDetail_BookRoom.getSelectionModel().select(c.getCustomerTypeId().get()-1);
						txtAddressDetail_BookRoom.setText(c.getPeopleAddress().get());
						
						flag = true;
						return;
					}
				}
				
				txtCustomerId_BookRoom.setText("KH_" + (CustomerDAO.listCustomer.size() + 1));
			}
		});
	
		btnOK_BookRoom.setOnMouseClicked(e->{
			if(listBookRoomTable.size()!=0) {
				addNewTenancyCard();
				if(!flag)
					addNewCustomer();
				
				init();
				fillRoom();
				listBookRoomTable.clear();
			}
				
		});
	
	}
	
	private void addNewCustomer() {
		String name = txtName_BookRoom.getText();
		String address = txtAddressDetail_BookRoom.getText();
		String governmentId = txtGovernmentIdDetail_BookRoom.getText();
		String id = txtCustomerId_BookRoom.getText();
		int type = cbbCustomerTypeDetail_BookRoom.getSelectionModel().getSelectedIndex() + 1;
		Customer c = new Customer(name, address, governmentId, id, type);
		
		try {
			CustomerDAO.addNewCustomer(c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addNewTenancyCard() {
		String bookRoomId = (BookRoomDAO.listBookRoom.size()+1) +"";
		String customerId = txtCustomerId_BookRoom.getText();
		String staffId = TemplateDAO.currentStaff.getStaffId().get();
		LocalDate startDay = LocalDate.now();
		
		BookRoom br = new BookRoom(bookRoomId, customerId, staffId, startDay);
		String temp = bookRoomId+"_";
		int count = 0;
		
		for(BookRoomTable brt : listBookRoomTable) {
			for(Room r : RoomDAO.listRoom) {
				if(brt.getRoomName().get().equals(r.getRoomName().get())) {
					listRoom.add(r);
					break;
				}
			}
			String bookRoomDetailId = temp + (++count);
			String roomId = brt.getRoomName().get();
			int numberOfGuests = Integer.parseInt(brt.getNumberOfGuests().get());
			
			listBookRoomDetail.add(new BookRoomDetail(bookRoomDetailId, roomId, bookRoomId, numberOfGuests));
		}
		
		
		
		
		try {
			BookRoomDAO.addNewBookRoom(br);
			RoomDAO.updateRoomToRented(listRoom);
			BookRoomDetailDAO.addNewBookRoomDetail(listBookRoomDetail);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void cancelOrder() {
		txtName_BookRoom.setText("");
		txtGovernmentIdDetail_BookRoom.setText("");
		cbbCustomerTypeDetail_BookRoom.getSelectionModel().select(0);
		txtAddressDetail_BookRoom.setText("");
		
		fillRoom();
		
		listBookRoomTable.clear();
	}
}
