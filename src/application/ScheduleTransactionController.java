package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ScheduleTransactionController implements Initializable, DataAccessLayer {

	@FXML
	private TextField scheduleNameInput;
	@FXML
	private TextField dueDateInput;
	@FXML
	private TextField paymentAmountInput;
	@FXML
	private ChoiceBox<String> accountInput;
	@FXML
	private ChoiceBox<String> transactionTypeInput;
	@FXML
	private ChoiceBox<String> frequencyInput;
	
	private Stage stage;
	private Scene scene;

	public void initialize(URL url, ResourceBundle resourceBundle) {

		try {
			populateAccountMenu("Accounts.csv");
			accountInput.setValue(accountInput.getItems().get(0));
		} 
		catch (IOException e) {
			System.out.println("Accounts.csv file not found!");
		}
		
		try {
			populateTransactionMenu("TransactionType.csv");
			transactionTypeInput.setValue(transactionTypeInput.getItems().get(0));
		}
		catch (IOException e) {
			System.out.println("TransactionType.csv file not found!");
		}
		
		frequencyInput.getItems().add("Monthly");
		frequencyInput.setValue(frequencyInput.getItems().get(0));

		return;
	}

	public void populateAccountMenu(String filepath) throws IOException {
		File file = new File(filepath);
		if(file.exists()) {
			//populate table
			BufferedReader br = new BufferedReader(new FileReader("Accounts.csv"));
			String line;
			while((line = br.readLine()) != null) {
				accountInput.getItems().add((line.split(","))[0]);
			}
		}
		else {
			throw new IOException();
		}
	}

	//populate the transaction menu from the TransactionType.csv file
	public void populateTransactionMenu(String filepath) throws IOException {
		File file = new File(filepath);
		if(file.exists()) {
			//populate table
			BufferedReader br = new BufferedReader(new FileReader("TransactionType.csv"));
			String line;
			while((line = br.readLine()) != null) {
				transactionTypeInput.getItems().add(line);
			}
		}
		else {
			throw new IOException();
		}
	}
	
	//this methods saves the user input to ScheduledTransaction.csv
	public void save(ActionEvent event) throws IOException {
		if(scheduleNameInput.getText().isEmpty() || dueDateInput.getText().isEmpty() || paymentAmountInput.getText().isEmpty() || accountInput.getValue() == null || transactionTypeInput.getValue() == null || frequencyInput.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please enter in all fields");
			Optional<ButtonType> result = alert.showAndWait();
			return;
		}
		
		try {
			Double.parseDouble(paymentAmountInput.getText());
		}
		catch(Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please enter a valid number payment amount!");
			Optional<ButtonType> result = alert.showAndWait();
			return;
		}
		try {
			Integer.parseInt(dueDateInput.getText());
		}
		catch(Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please enter a valid integer for due date!");
			Optional<ButtonType> result = alert.showAndWait();
			return;
		}
		
		File file = new File("ScheduledTransactions.csv");
		
		//???
		if(file.exists() && checkForDuplicates("ScheduledTransactions.csv", scheduleNameInput.getText())) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("An account with that name already exists!");
			Optional<ButtonType> result = alert.showAndWait();
			return;
		}
		
		saveScheduleTransactionInfo(scheduleNameInput.getText(), accountInput.getValue(), transactionTypeInput.getValue(), frequencyInput.getValue(), dueDateInput.getText(), paymentAmountInput.getText());
		Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	
	public void switchToHomeScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
