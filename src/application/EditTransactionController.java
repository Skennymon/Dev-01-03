package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
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

import java.text.SimpleDateFormat;

public class EditTransactionController implements Initializable, DataAccessLayer {

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
	private Transaction transaction; //the current transaction being edited
	private ObservableList<Transaction> list;
	private Stage stage;
	private Scene scene;

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
		
		return;
	}
	
	
	
	

	public void save(ActionEvent event) throws IOException {
		/*
		 * Copy and Paste the save() from newTransactionController
		 * 
		 * Add logic to check if the new inputted transaction name is already in transaction.csv
		 * If it isn't, then overwrite transaction.csv using ObservableList<Transaction> list
		 * BTW only run the logic for checkDuplicates() if and only if the new inputted transaction name isn't the same as the previous one
		 */
		
		//check if user entered all fields, if checks if either deposit or payment or both are filled in correctly and saves it if its good
		if(transactionDescriptionInput.getText().isEmpty() || accountDropdownInput.getItems().isEmpty() || transactionTypeDropdownInput.getItems().isEmpty() || transactionDateInput.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please enter in all fields");
			Optional<ButtonType> result = alert.showAndWait();
		}
		else if(paymentAmountInput.getText().isEmpty() && depositAmountInput.getText().isEmpty()) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please enter valid payment amount or deposit amount or both!");
			Optional<ButtonType> result = alert.showAndWait();

		}
		else if(!depositAmountInput.getText().isEmpty() && !paymentAmountInput.getText().isEmpty()) {
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
			//edit this
			editSaveTransactionInfo(accountDropdownInput.getValue(), transactionTypeDropdownInput.getValue(), transactionDateInput.getValue(), transactionDescriptionInput.getText(), paymentAmountInput.getText(), depositAmountInput.getText(), list, transaction);
			Parent root = FXMLLoader.load(getClass().getResource("ViewTransactionScene.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		else if (depositAmountInput.getText().isEmpty() && !paymentAmountInput.getText().isEmpty()) {
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
			//edit this
			editSaveTransactionInfo(accountDropdownInput.getValue(), transactionTypeDropdownInput.getValue(), transactionDateInput.getValue(), transactionDescriptionInput.getText(), paymentAmountInput.getText(), depositAmountInput.getText(), list, transaction);
			Parent root = FXMLLoader.load(getClass().getResource("ViewTransactionScene.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		else {
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
			//edit this to checkDuplicates and change method()
			
			editSaveTransactionInfo(accountDropdownInput.getValue(), transactionTypeDropdownInput.getValue(), transactionDateInput.getValue(), transactionDescriptionInput.getText(), paymentAmountInput.getText(), depositAmountInput.getText(), list, transaction);
			Parent root = FXMLLoader.load(getClass().getResource("ViewTransactionScene.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
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

	public void setTransaction(Transaction transaction, ObservableList<Transaction> list) {
		this.transaction = transaction;
		this.list = list;
		accountDropdownInput.setValue(transaction.getAccount());
		transactionTypeDropdownInput.setValue(transaction.getTransactionType());
		transactionDateInput.setValue(LocalDate.parse(transaction.getTransactionDate()));
		transactionDescriptionInput.setText(transaction.getTransactionDescription().trim());
		paymentAmountInput.setText(transaction.getPaymentAmount().trim());
		depositAmountInput.setText(transaction.getDepositAmount().trim()); 
	}
	
	public void switchToHomeScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToViewTransactionScene(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewTransactionScene.fxml"));
		Parent root = loader.load();
		
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
