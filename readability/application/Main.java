package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	private static Stage primaryStage;
	private AnchorPane rootLayout;	
	
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("中文阅读指数分析（是光™特别版）");
		
		try {
			//Load main layout from fxml
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/view/MainStage.fxml"));
			rootLayout = (AnchorPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			
			primaryStage.show();
			AppController controller = loader.getController();
			controller.setMainApp(this);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
    
    public void showLoadFileDialog(TextArea InputArea) {
    	try {
    		// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/LoadFileLayout.fxml"));
			VBox page = (VBox) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Load File");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set reference to stage in controller
			LoadFileDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			
			// give controller reference to text area to load file into
			controller.setTextArea(InputArea);

			// Show the dialog and wait until the user closes it
		    dialogStage.showAndWait();
		    
		    
		

    	} catch (IOException e) {
    		// Exception gets thrown if the fxml file could not be loaded
    		e.printStackTrace();
    	}
    	
    }
    
	
	public static void main(String[] args) {
		launch(args);
	}

	public static void showInputErrorDialog(String inErr) {
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("这是一个错误提示");
		alert.setHeaderText("您的输入不正确");
		alert.setContentText(inErr);

		alert.showAndWait();
		
	}
	
	public static void showClassifyDialog(String s) {
		try {
    		// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/view/ClassifyDialog.fxml"));
			loader.setController(new DialogController());
			
			DialogController controller = loader.getController();
			AnchorPane classifyDia = (AnchorPane)loader.load();
			
			
			Stage dialogStage = new Stage();
			Scene scene = new Scene(classifyDia);
			dialogStage.setTitle("生字分级结果");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setScene(scene);
			
			controller.setDialogStage(dialogStage);
//			controller.setMainApp(this);
			controller.setField(s);
			
		    dialogStage.showAndWait();
		    
		    
		

    	} catch (IOException e) {
    		// Exception gets thrown if the fxml file could not be loaded
    		e.printStackTrace();
    	}
	}
	
	public Stage getStage() {
		return this.primaryStage;
	}
}
