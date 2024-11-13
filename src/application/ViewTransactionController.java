package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewTransactionController implements Initializable {
	
	@FXML
	private TableView<Transaction> transactionTable;
	@FXML
	private TableColumn<Transaction, String> accountCol;
	@FXML
	private TableColumn<Transaction, String> transactionTypeCol;
	@FXML
	private TableColumn<Transaction, String> transactionDateCol;
	@FXML
	private TableColumn<Transaction, String> transactionDescriptionCol;
	@FXML
	private TableColumn<Transaction, String> paymentAmountCol;
	@FXML
	private TableColumn<Transaction, String> depositAmountCol;
	
	public void initialize(URL url, ResourceBundle bundle) {
		ObservableList<Transaction> accountList = FXCollections.observableArrayList();
		
		accountCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("account"));
		transactionTypeCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("transactionType"));
		transactionDateCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("transactionDate"));
		transactionDescriptionCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("transactionDescription"));
		paymentAmountCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("paymentAmount"));
		depositAmountCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("depositAmount"));
		
		accountList = loadTransactionData();
		transactionTable.setItems(accountList);
		
		transactionDateCol.setSortType(TableColumn.SortType.DESCENDING);
		transactionTable.getSortOrder().add(transactionDateCol);
		transactionTable.sort();
	}
	
	public ObservableList<Transaction> loadTransactionData() {
		ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
		String filePath = "Accounts.csv";
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("Transactions.csv"));
			String line;
			while((line = br.readLine()) != null) {
				String[] transaction = line.split(",");
				transactionList.add(new Transaction(transaction[0], transaction[1], transaction[2], transaction[3], transaction[4], transaction[5]));
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please add some transactions!");
			Optional<ButtonType> result = alert.showAndWait();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return transactionList;
	}
	
}
