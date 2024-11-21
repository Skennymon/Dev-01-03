package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditScheduledTransactionController implements Initializable, DataAccessLayer {
	
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
	
	private ScheduleTransactionBean scheduledTransaction;
	
	private Stage stage;
	private Scene scene;
	
	
	public void initialize(URL url, ResourceBundle bundle) {
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
				transactionTypeInput.getItems().add(line);
			}
		}
		else {
			return;
		}
	}
	
	public void setScheduledTransaction(ScheduleTransactionBean scheduledTransaction) {
		this.scheduledTransaction = scheduledTransaction;
		accountInput.setValue(scheduledTransaction.getAccount());
		scheduleNameInput.setText(scheduledTransaction.getScheduleName());
		transactionTypeInput.setValue(scheduledTransaction.getTransactionType());
		dueDateInput.setText(scheduledTransaction.getDueDate());
		paymentAmountInput.setText(scheduledTransaction.getPaymentAmount());
	}
	
}
