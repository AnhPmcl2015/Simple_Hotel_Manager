package bus;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import dao.BookRoomDAO;
import dao.BookRoomDetailDAO;
import dao.CustomerDAO;
import dao.StaffDAO;
import dto.BookRoom;
import dto.BookRoomDetail;
import dto.Customer;
import dto.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import tablemodel.BookRoomTable;
import tablemodel.SearchBookRoomTable;

public class SearchBookRoomController implements Initializable {

	private ObservableList<SearchBookRoomTable> listBookRoom, listTemp;
	private ObservableList<BookRoomTable> listBookRoomDetail;
	private SearchBookRoomTable temp;

	@FXML
	private GridPane gridDetail_BookRoom;

	@FXML
	private TextField txtCustomerId, txtName, txtGovernmentId, txtCustomerType, txtStaffName, txtDate,
			txtTenancyCardIdSearch;

	@FXML
	private TextArea txtAddressDetail;

	@FXML
	private TableView<BookRoomTable> tableTenancyCardDetail;

	@FXML
	private TableColumn<BookRoomTable, String> tableColumnRoomName, tableColumnNumberOfGuest;

	@FXML
	private TableView<SearchBookRoomTable> tableTenancyCard;

	@FXML
	private TableColumn<SearchBookRoomTable, String> tableColumnID, tableColumnStaff, tableColumnCustomer,
			tableColumnDate, tableColumnCustomerID;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		addControls();
		addEvents();
	}

	private void addControls() {
		listBookRoom = FXCollections.observableArrayList();
		listTemp = FXCollections.observableArrayList();
		listBookRoomDetail = FXCollections.observableArrayList();

		tableColumnID.setCellValueFactory(cellData -> cellData.getValue().getId());
		tableColumnStaff.setCellValueFactory(cellData -> cellData.getValue().getStaff());
		tableColumnCustomer.setCellValueFactory(cellData -> cellData.getValue().getCustomer());
		tableColumnDate.setCellValueFactory(cellData -> cellData.getValue().getDate());
		tableColumnCustomerID.setCellValueFactory(cellData -> cellData.getValue().getCustomerId());
		tableTenancyCard.setItems(listTemp);

		tableColumnRoomName.setCellValueFactory(cellData -> cellData.getValue().getRoomName());
		tableColumnNumberOfGuest.setCellValueFactory(cellData -> cellData.getValue().getNumberOfGuests());
		tableTenancyCardDetail.setItems(listBookRoomDetail);

		fillTable();

		if (listTemp.size() != 0) {
			temp = new SearchBookRoomTable(listTemp.get(0));
			changeDetail();

			tableTenancyCard.getSelectionModel().select(0);
		}

	}

	private void fillTable() {
		listBookRoom.clear();
		listTemp.clear();

		for (BookRoom b : BookRoomDAO.listBookRoom) {
			String id = b.getBookRoomId().get();
			String staff = "";
			for (Staff s : StaffDAO.listStaff) {
				if (s.getStaffId().equals(b.getStaffId())) {
					staff = s.getPeopleName().get();
					break;
				}
			}

			String customer = "";
			String customerId = "";
			for (Customer c : CustomerDAO.listCustomer) {
				if (c.getCustomerId().equals(b.getCustomerId())) {
					customer = c.getPeopleName().get();
					customerId = c.getCustomerId().get();
					break;
				}
			}

			String date = b.getStartDay().get().format(DateTimeFormatter.ofPattern("dd"));

			listTemp.add(new SearchBookRoomTable(id, staff, customer, date, customerId));
			listBookRoom.add(new SearchBookRoomTable(id, staff, customer, date, customerId));
		}
	}

	private void changeDetail() {
		listBookRoomDetail.clear();
		if (listTemp.size() == 0)
			return;

		for (Customer c : CustomerDAO.listCustomer) {
			if (c.getCustomerId().get().equals(temp.getCustomerId().get())) {
				txtCustomerId.setText(c.getCustomerId().get());
				txtName.setText(c.getPeopleName().get());
				txtGovernmentId.setText(c.getGovernmentId().get());
				String type = "";
				if (c.getCustomerTypeId().get() == 1)
					type = "Native";
				else
					type = "Foreign";
				txtCustomerType.setText(type);
				txtAddressDetail.setText(c.getPeopleAddress().get());
				break;
			}
		}

		txtStaffName.setText(temp.getStaff().get());
		txtDate.setText(temp.getDate().get());

		for (BookRoomDetail brd : BookRoomDetailDAO.listBookRoomDetail) {
			if (brd.getBookRoomID().get().equals(temp.getId().get())) {
				String name = brd.getRoomId().get();
				int numberOfGuest = brd.getNumberOfGuests().get();

				listBookRoomDetail.add(new BookRoomTable(name, numberOfGuest + ""));
			}
		}
	}

	private void addEvents() {
		tableTenancyCard.setOnMouseClicked(e -> {
			temp = listTemp.get(tableTenancyCard.getSelectionModel().getSelectedIndex());
			changeDetail();
		});

		txtTenancyCardIdSearch.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				searchTenancyCardFollowingId(txtTenancyCardIdSearch.getText());
			}
		});
	}

	private void searchTenancyCardFollowingId(String text) {
		listTemp.clear();
		if ("".equals(text)) {
			for (SearchBookRoomTable s : listBookRoom)
				listTemp.add(s);
			temp = listTemp.get(0);
			changeDetail();
			return;
		}

		for (SearchBookRoomTable b : listBookRoom) {
			if (b.getId().get().contains(text))
				listTemp.add(b);

			if (listTemp.size() > 0) {
				temp = listTemp.get(0);
				changeDetail();
			} else {
				listBookRoomDetail.clear();
				txtCustomerId.setText("");
				txtName.setText("");
				txtGovernmentId.setText("");
				txtCustomerType.setText("");
				txtAddressDetail.setText("");
				txtStaffName.setText("");
				txtDate.setText("");
			}
		}
	}
}
