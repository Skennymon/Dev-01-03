package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Accounts.csv not found!");
		}
		
		try {
			populateTransactionMenu("TransactionType.csv");
		}
		catch (IOException e) {
			System.out.println("TransactionType.csv not found!");
		}
		
	}
	
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
	
}
