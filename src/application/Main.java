package application;
	
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root,300,150);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Login");
			
			primaryStage.setResizable(false);
			primaryStage.setMaximized(false);
			
			primaryStage.setOnCloseRequest(e->{
				Alert dialog = new Alert(AlertType.CONFIRMATION);
				dialog.setTitle("Exit application");
				dialog.setHeaderText("Are you sure want to exit?");
				Optional<ButtonType> option = dialog.showAndWait(); 
				
				if(option.get() == ButtonType.OK) {
					Platform.exit();
				}else if(option.get() == ButtonType.CANCEL){
					dialog.close();
					e.consume();
				}
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
