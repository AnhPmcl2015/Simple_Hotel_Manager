package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.BookRoomDAO;
import dao.BookRoomDetailDAO;
import dao.CustomerDAO;
import dao.CustomerTypeDAO;
import dao.RoomDAO;
import dao.RoomStatusDAO;
import dao.RoomTypeDAO;
import dao.StaffDAO;
import dao.TemplateDAO;
import dto.Manager;
import dto.Receptionist;
import dto.Staff;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController implements Initializable {

	@FXML
	private TextField txtUsername, txtPassword;

	@FXML
	private Button btnLogin, btnCancel;

	@FXML
	private Text txtError;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		addControls();
		addEvents();

	}

	private void addControls() {
		try {
			if (StaffDAO.listStaff == null) {
				StaffDAO.getAllStaff();
			}

			if (RoomDAO.listRoom == null) {
				RoomDAO.getAllRoom();
			}
			
			if(RoomDAO.listRoomWithoutDeleted==null) {
				RoomDAO.getAllRoomWithoutDeleted();
			}
			
			
			if(RoomTypeDAO.listRoomType == null	) {
				RoomTypeDAO.getAllRoomType();
			}
			
			if(RoomTypeDAO.listAllRoomType == null) {
				RoomTypeDAO.getAllRoomTypeWithoutDeleted();
			}
			
			if(RoomStatusDAO.listRoomStatus==null) {
				RoomStatusDAO.getAllRoomStatus();
			}
			
			if(CustomerTypeDAO.listCustomerType == null) {
				CustomerTypeDAO.getCustomerType();
			}
			
			if(CustomerDAO.listCustomer == null) {
				CustomerDAO.getCustomer();
			}
			
			if(TemplateDAO.surcharge == 0f) {
				TemplateDAO.getTemplate();
			}
			
			if(BookRoomDAO.listBookRoom==null) {
				BookRoomDAO.getAllBookRoom();
			}
			
			if(BookRoomDetailDAO.listBookRoomDetail	== null) {
				BookRoomDetailDAO.getAllBookRoomDetail();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void addEvents() {
		btnLogin.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			try {
				login();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		btnLogin.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			if (e.getCode() == KeyCode.ENTER) {
				try {
					login();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		txtUsername.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			if (e.getCode() == KeyCode.ENTER) {
				try {
					login();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		txtPassword.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			if (e.getCode() == KeyCode.ENTER) {
				try {
					login();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnCancel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			Alert dialog = new Alert(AlertType.CONFIRMATION);
			dialog.setTitle("Exit application");
			dialog.setHeaderText("Are you sure want to exit?");
			Optional<ButtonType> option = dialog.showAndWait();

			if (option.get() == ButtonType.OK) {
				Platform.exit();
			} else if (option.get() == ButtonType.CANCEL) {
				dialog.close();
				e.consume();
			}
		});
	}

	private void login() throws IOException {
		if ("".equals(checkCondition())) {
			Stage stage = (Stage) btnLogin.getScene().getWindow();
			stage.close();

			Stage stage1 = new Stage();

			Parent root = FXMLLoader.load(getClass().getResource("/gui/MainScreen.fxml"));
			Scene scene = new Scene(root, 1300, 700);
			stage1.setScene(scene);
			stage1.initModality(Modality.APPLICATION_MODAL);

			stage1.show();

			stage1.setOnCloseRequest(e -> {
				Alert dialog = new Alert(AlertType.CONFIRMATION);
				dialog.setTitle("Exit application");
				dialog.setHeaderText("Are you sure want to exit?");
				Optional<ButtonType> option = dialog.showAndWait();

				if (option.get() == ButtonType.OK) {
					Platform.exit();
				} else if (option.get() == ButtonType.CANCEL) {
					dialog.close();
					e.consume();
				}
			});

		}
	}

	private String checkCondition() {
		String error = "";
		if (txtPassword.getText().trim().equals(""))
			error = "Password cannot empty\n";
		if (txtUsername.getText().trim().equals(""))
			error = error + "Username cannot empty\n";
		for (Staff staff : StaffDAO.listStaff) {
			if ((txtUsername.getText().trim().equals(staff.getUsername().get())
					&& txtPassword.getText().trim().equals(staff.getPassword().get()))) {
				if (staff.getIsManager().get()) {
					TemplateDAO.currentStaff = new Manager(staff.getPeopleName().get(), staff.getPeopleAddress().get(),
							staff.getGovernmentId().get(), staff.getStaffId().get(), staff.getUsername().get(),
							staff.getIsManager().get(), staff.getPassword().get());
				} else {
					TemplateDAO.currentStaff = new Receptionist(staff.getPeopleName().get(),
							staff.getPeopleAddress().get(), staff.getGovernmentId().get(), staff.getStaffId().get(),
							staff.getUsername().get(), staff.getIsManager().get(), staff.getPassword().get());
				}
			}
		}

		if (TemplateDAO.currentStaff == null) {
			error = error + "Username and password don't match";
		}

		txtError.setText(error);
		return error;
	}

}
