package bus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.TemplateDAO;
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
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainScreenController implements Initializable {
	
	@FXML
	private Text txtStaff;

	@FXML
	private Button btnLogout, btnBookRoom, btnSearchTenancyCardDetail, btnSearchCustomer, btnManageRooms, 
	btnManageStaffs, btnManageRegulations;

	@FXML
	private TabPane tab;


	private SingleSelectionModel<Tab> selectionModel;
	private static int flagForBookRoom, flagForSearchTenancyCard, flagForSearchCustomer, flagForManageRooms, flagForManageStaffs, flagForManageRegulations;
	private static int flag = -1;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			addControls();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addEvents();
	}

	private void addControls() throws FileNotFoundException {
		selectionModel = tab.getSelectionModel();
		flagForBookRoom = -1;
		flagForSearchTenancyCard = -1;
		flagForSearchCustomer = -1;
		flagForManageRooms = -1;
		flagForManageStaffs = -1;
		flagForManageRegulations = -1;
		
		FileInputStream input = new FileInputStream("resources/bookroom.png");
		Image image = new Image(input);
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		btnBookRoom.setGraphic(imageView);
		
		input = new FileInputStream("resources/search.png");
		image = new Image(input);
		imageView = new ImageView(image);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		btnSearchTenancyCardDetail.setGraphic(imageView);
		
		input = new FileInputStream("resources/customer.png");
		image = new Image(input);
		imageView = new ImageView(image);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		btnSearchCustomer.setGraphic(imageView);
		
		input = new FileInputStream("resources/roommanager.png");
		image = new Image(input);
		imageView = new ImageView(image);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		btnManageRooms.setGraphic(imageView);
		
		input = new FileInputStream("resources/staff.png");
		image = new Image(input);
		imageView = new ImageView(image);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		btnManageStaffs.setGraphic(imageView);
		
		input = new FileInputStream("resources/regulations.png");
		image = new Image(input);
		imageView = new ImageView(image);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		btnManageRegulations.setGraphic(imageView);
		
		Staff user = TemplateDAO.currentStaff;
		if (user.getIsManager().get()) {
			txtStaff.setText(user.getPeopleName().get() + " _ Manager");
		} else {
			txtStaff.setText(user.getPeopleName().get() + " _ Receptionist");
		}
		
		if(!user.getIsManager().get()) {
			btnSearchCustomer.setDisable(true);
			btnSearchTenancyCardDetail.setDisable(true);
			btnManageRegulations.setDisable(true);
			btnManageRooms.setDisable(true);
			btnManageStaffs.setDisable(true);
		}
	}

	private void addEvents() {
		btnLogout.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			logoutEvent(e);
		});
		
		btnBookRoom.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
			try {
				showBookRoomWindow();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		btnManageRooms.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
			try {
				showRoomManagerWindow();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		btnManageStaffs.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
			try {
				showStaffManagerWindow();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		btnManageRegulations.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
			try {
				showRegulationsManagerWindow();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		btnSearchCustomer.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
			try {
				showSearchCustomerWindow();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		btnSearchTenancyCardDetail.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
			try {
				showSearchTenancyCardWindow();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	private void logoutEvent(MouseEvent e) {
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle("Log out");
		dialog.setHeaderText("Are you sure want to log out?");
		Optional<ButtonType> option = dialog.showAndWait();

		if (option.get() == ButtonType.OK) {
			Stage stage = (Stage) btnLogout.getScene().getWindow();
			stage.close();
			
			TemplateDAO.currentStaff = null;
			
			Stage stage1 = new Stage();

			try {
				Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
				Scene scene = new Scene(root, 300, 150);
				stage1.setResizable(false);
				stage1.setMaximized(false);
				
				stage1.setTitle("Log in");
				stage1.setScene(scene);
				stage1.initModality(Modality.APPLICATION_MODAL);
			} catch (Exception e3) {

			}
			stage1.show();

			stage1.setOnCloseRequest(e1 -> {
				Alert dialog1 = new Alert(AlertType.CONFIRMATION);
				dialog1.setTitle("Exit application");
				dialog1.setHeaderText("Are you sure want to exit?");
				Optional<ButtonType> option1 = dialog1.showAndWait();

				if (option1.get() == ButtonType.OK) {
					Platform.exit();
				} else if (option1.get() == ButtonType.CANCEL) {
					dialog1.close();
					e1.consume();
				}
			});

		} else if (option.get() == ButtonType.CANCEL) {
			dialog.close();
			e.consume();
		}

	}

	private void showSearchTenancyCardWindow() throws IOException {
		if(flagForSearchTenancyCard == -1) {
			Tab tab1 = new Tab();
			tab1.setText("Searching tenancy card");
			
			Parent root = FXMLLoader.load(getClass().getResource("/gui/SearchBookRoom.fxml"));
			tab1.setContent(root);
			tab.getTabs().add(tab1);
			
			flag++;
			flagForSearchTenancyCard = flag;
			
			tab1.setOnCloseRequest(e->{
				flag--;
				flagForSearchTenancyCard = -1;
			});
		}
		selectionModel.select(flagForSearchTenancyCard);
	}
	
	private void showSearchCustomerWindow() throws IOException {
		if(flagForSearchCustomer == -1) {
			Tab tab1 = new Tab();
			tab1.setText("Searching customer");
			
			Parent root = FXMLLoader.load(getClass().getResource("/gui/SearchCustomer.fxml"));
			tab1.setContent(root);
			tab.getTabs().add(tab1);
			
			flag++;
			flagForSearchCustomer = flag;
			
			tab1.setOnCloseRequest(e->{
				flag--;
				flagForSearchCustomer = -1;
			});
		}
		selectionModel.select(flagForSearchCustomer);
	}
	
	private void showBookRoomWindow() throws IOException {
		if(flagForBookRoom == -1) {
			Tab tab1 = new Tab();
			tab1.setText("Booking room");
			
			Parent root = FXMLLoader.load(getClass().getResource("/gui/BookRoom.fxml"));
			tab1.setContent(root);
			
			tab.getTabs().add(tab1);
			
			flag++;
			flagForBookRoom = flag;
			
			tab1.setOnCloseRequest(e->{
				flag--;
				flagForBookRoom = -1;
			});
		}
		selectionModel.select(flagForBookRoom);
	}
	
	private void showRoomManagerWindow() throws IOException {
		if(flagForManageRooms == -1) {
			Tab tab1 = new Tab();
			tab1.setText("Rooms Manager");
			
			Parent root = FXMLLoader.load(getClass().getResource("/gui/RoomManager.fxml"));
			tab1.setContent(root);
			
			tab.getTabs().add(tab1);
			
			flag++;
			flagForManageRooms = flag;
			
			tab1.setOnCloseRequest(e->{
				flag--;
				flagForManageRooms = -1;
			});
		}
		selectionModel.select(flagForManageRooms);
	}
	
	private void showStaffManagerWindow() throws IOException {
		if(flagForManageStaffs == -1) {
			Tab tab1 = new Tab();
			tab1.setText("Staffs Manager");
			
			Parent root = FXMLLoader.load(getClass().getResource("/gui/Staff Manager.fxml"));
			tab1.setContent(root);
			
			tab.getTabs().add(tab1);
			
			flag++;
			flagForManageStaffs = flag;
			
			tab1.setOnCloseRequest(e->{
				flag--;
				flagForManageStaffs = -1;
			});
		}
		selectionModel.select(flagForManageStaffs);
	}
	
	private void showRegulationsManagerWindow() throws IOException {
		if(flagForManageRegulations == -1) {

			Tab tab1 = new Tab();
			tab1.setText("Regulations Manager");
			
			Parent root = FXMLLoader.load(getClass().getResource("/gui/ChangeRegulations.fxml"));
			tab1.setContent(root);
			
			tab.getTabs().add(tab1);
			
			flag++;
			flagForManageRegulations = flag;
			
			tab1.setOnCloseRequest(e->{
				flag--;
				flagForManageRegulations = -1;
			});
		}
		selectionModel.select(flagForManageRegulations);
	}
}
