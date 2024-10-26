package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SceneController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	final String CSV_LOCATION = "Accounts.csv";

	@FXML
	private TextField accountNameInput;
	@FXML
	private TextField accountOpeningBalanceInput;
	@FXML
	private DatePicker accountOpeningInput;
	
	public void switchtoHomeScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToNewAccountScene(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("NewAccountScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToViewAccountScene(ActionEvent event) throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAccountScene.fxml"));
		root = loader.load();

		ViewAccountController viewAccountController = loader.getController();

		// start back here homie

		// Parent root =
		// FXMLLoader.load(getClass().getResource("ViewAccountScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void save(ActionEvent event) throws IOException {

		// if the user hasn't entered all the required fields, it gives an alert to the
		// user that they need to do that
		if (accountNameInput.getText().isEmpty() || accountOpeningBalanceInput.getText().isEmpty()
				|| accountOpeningInput.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please enter in all fields");
			Optional<ButtonType> result = alert.showAndWait();
		} else {

			// saves account info to a new file
			saveAccountInfo(accountNameInput.getText(), accountOpeningInput.getValue(),
					accountOpeningBalanceInput.getText());

			Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}

	}

	// saves whatever the user inputted into a csv file located in the root folder
	// called "Accounts.csv"
	public void saveAccountInfo(String name, LocalDate date, String balance) {
		try (FileWriter writer = new FileWriter("Accounts.csv", true)) {
			writer.write(name + "," + date.toString() + "," + balance + "\n");
			writer.flush();
			System.out.println("Data Saved to Accounts.csv");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
