package application;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SceneController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML 
	private TextField accountNameInput;
	@FXML
	private TextField accountOpeningBalanceInput;
	@FXML
	private DatePicker accountOpeningInput;
	
	public void switchtoHomeScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToNewAccountScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("NewAccountScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToViewAccountScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ViewAccountScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void promptUserToEnterAllFields(ActionEvent event) throws IOException {
		
		//if the user hasn't entered all the required fields, it gives an alert to the user that they need to do that
		if(accountNameInput.getText().isEmpty() || accountOpeningBalanceInput.getText().isEmpty() || accountOpeningInput.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please enter in all fields");
			Optional<ButtonType> result = alert.showAndWait();
		}
		else {
			Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		
	}
	
	
	
	
}
