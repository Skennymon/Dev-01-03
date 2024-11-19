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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ViewTransactionController implements Initializable, DataAccessLayer {
	
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
	@FXML
	private TextField keywordTextField;
	
	private Parent root;
	private Scene scene;
	private Stage stage;
	
	public void initialize(URL url, ResourceBundle bundle) {
		ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
		
		//sets each column to its respective attribute
		accountCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("account"));
		transactionTypeCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("transactionType"));
		transactionDateCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("transactionDate"));
		transactionDescriptionCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("transactionDescription"));
		paymentAmountCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("paymentAmount"));
		depositAmountCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("depositAmount"));
		
		transactionList = loadTransactionData();
		transactionTable.setItems(transactionList);
		
		//sets the table to descending based on transaction date
		transactionDateCol.setSortType(TableColumn.SortType.DESCENDING);
		transactionTable.getSortOrder().add(transactionDateCol);
		transactionTable.sort();
		
		//implements search functionality
		FilteredList<Transaction> filteredData = new FilteredList<>(transactionList, b -> true);
		keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Transaction -> {
				if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
					return true;
				}
				
				String searchKeyWord = newValue.toLowerCase();
				if(Transaction.getTransactionDescription().toLowerCase().indexOf(searchKeyWord) > -1) {
					return true;
				}
				return false;
			});
		});
		
		SortedList<Transaction> sortedData = new SortedList<>(filteredData);
		
		sortedData.comparatorProperty().bind(transactionTable.comparatorProperty());
		
		transactionTable.setItems(sortedData);
		
		
		
	}
	
	
	public void switchToHomeScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
}
