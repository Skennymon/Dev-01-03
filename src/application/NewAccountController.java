package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewAccountController implements Initializable {
	
	private Stage stage;
	private Scene scene;
	
	@FXML
	private TextField accountNameInput;
	@FXML
	private TextField accountOpeningBalanceInput;
	@FXML
	private DatePicker accountOpeningInput;
	
	public void initialize(URL url, ResourceBundle resourceBundle) {
		accountOpeningInput.setValue(LocalDate.now());
	}
	
	public void save(ActionEvent event) throws IOException {

		// if the user hasn't entered all the required fields, it gives an alert to the
		// user that they need to do that
		File file = new File("Accounts.csv");
		
		//try catch block checks if user entered a valid number for opening balance
		try {
			Double.parseDouble(accountOpeningBalanceInput.getText());
		}
		catch(Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please enter a valid number for opening balance!");
			Optional<ButtonType> result = alert.showAndWait();
			return;
		}

		if (accountNameInput.getText().isEmpty() || accountOpeningBalanceInput.getText().isEmpty() || accountOpeningInput.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please enter in all fields");
			Optional<ButtonType> result = alert.showAndWait();
		}
		else if(file.exists() && checkForDuplicates()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("An account with that name already exists!");
			Optional<ButtonType> result = alert.showAndWait();
		}
		else {
			saveAccountInfo(accountNameInput.getText(), accountOpeningInput.getValue(),
					accountOpeningBalanceInput.getText());

			Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();

		}
		
	}
	
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
	
	public boolean checkForDuplicates() throws IOException {
		File file = new File("Accounts.csv");

		String line;
		BufferedReader br = new BufferedReader(new FileReader("Accounts.csv"));
		while((line = br.readLine()) != null) {
			if(line.toLowerCase().contains(accountNameInput.getText().toLowerCase())) {
				return true;
			}
		}


		return false;
	}
	
	
	public void switchToHomeScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
