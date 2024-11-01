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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewTransactionController implements Initializable {
	
	private Stage stage;
	private Scene scene;
	
	@FXML
	private ChoiceBox<String> accountDropdownInput;
	@FXML
	private ChoiceBox<String> transactionTypeDropdownInput;
	@FXML
	private DatePicker transactionDateInput;
	@FXML
	private TextField transactionDescriptionInput;
	@FXML
	private TextField paymentAmountInput;
	@FXML
	private TextField depositAmountInput;
	
	public void initialize(URL url, ResourceBundle bundle) {
		transactionDateInput.setValue(LocalDate.now());
		try {
			populateAccountMenu("Accounts.csv");
			accountDropdownInput.setValue(accountDropdownInput.getItems().get(0));
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Accounts.csv not found!");
		}
		catch (IndexOutOfBoundsException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please define an account first!");
			Optional<ButtonType> result = alert.showAndWait();
			
		}
		
		try {
			populateTransactionMenu("TransactionType.csv");
			transactionTypeDropdownInput.setValue(transactionTypeDropdownInput.getItems().get(0));
		}
		catch (IOException e) {
			System.out.println("TransactionType.csv not found!");
		}
		catch(IndexOutOfBoundsException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please define a transactiontype first!");
			Optional<ButtonType> result = alert.showAndWait();
		}
		
	}
	
	//grabs the account.csv file and populates the accountmenu with the accounts
	public void populateAccountMenu(String filepath) throws IOException {
		File file = new File(filepath);
		if(file.exists()) {
			//populate table
			BufferedReader br = new BufferedReader(new FileReader("Accounts.csv"));
			String line;
			while((line = br.readLine()) != null) {
				accountDropdownInput.getItems().add((line.split(","))[0]);
			}
		}
		else {
			return;
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
				transactionTypeDropdownInput.getItems().add(line);
			}
		}
		else {
			return;
		}
	}
	
	//saves to a new Transactions.csv file
	public void save(ActionEvent event) throws IOException {
		
		//check if user entered all fields
		if(transactionDescriptionInput.getText().isEmpty() || paymentAmountInput.getText().isEmpty() || depositAmountInput.getText().isEmpty() || accountDropdownInput.getItems().isEmpty() || transactionTypeDropdownInput.getItems().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please enter in all fields");
			Optional<ButtonType> result = alert.showAndWait();
		}
		else {
			
			//check if the user entered valid data
			try {
				Double.parseDouble(paymentAmountInput.getText());
			}
			catch (Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Alert!");
				alert.setContentText("Please enter a valid number payment amount!");
				Optional<ButtonType> result = alert.showAndWait();
				return;
			}
			
			try {
				Double.parseDouble(depositAmountInput.getText());
			}
			catch(Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Alert!");
				alert.setContentText("Please enter a valid number deposit amount!");
				Optional<ButtonType> result = alert.showAndWait();
				return;
			}
			
			saveTransactionInfo(accountDropdownInput.getValue(), transactionTypeDropdownInput.getValue(), transactionDateInput.getValue(), transactionDescriptionInput.getText(), paymentAmountInput.getText(), depositAmountInput.getText());
			Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		
	}
	
	public void switchToHomeScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void saveTransactionInfo(String account, String transactionType, LocalDate transactionDate, String transactionDescription, String paymentAmount, String depositAmount) {
		try (FileWriter writer = new FileWriter("Transactions.csv", true)) {
			writer.write(account + "," + transactionType + "," + transactionDate + "," + transactionDescription + "," + paymentAmount + "," + depositAmount + "\n");
			writer.flush();
			System.out.println("Data Saved to Transactions.csv");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
